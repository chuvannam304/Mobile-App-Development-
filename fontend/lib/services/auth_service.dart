import 'package:dio/dio.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:flutter_facebook_auth/flutter_facebook_auth.dart';
import '../utils/constants.dart';
import 'token_service.dart';

class AuthService {
  final Dio dio = Dio();
  final TokenService tokenService = TokenService();
  final FirebaseAuth _auth = FirebaseAuth.instance;

  // =========================
  // TEST CONNECT BACKEND
  // =========================
  Future<bool> testConnection() async {
    try {
      final res = await dio.get("${Constants.baseUrl}/ping");
      print("JAVA: ${res.data}");

      final db = await dio.get("${Constants.baseUrl}/db-test");
      print("DB: ${db.data}");

      return true;
    } catch (e) {
      print("CONNECT ERROR: $e");
      return false;
    }
  }

  // =========================
  // LOGIN EMAIL
  // =========================
  Future<String?> login(String email, String password) async {
    try {
      final res = await dio.post(
        "${Constants.baseUrl}/login",
        data: {"email": email, "password": password},
      );

      print("LOGIN RESPONSE: ${res.data}");

      final token = res.data["token"] ?? res.data["jwt"];

      if (token != null && token.isNotEmpty) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } catch (e) {
      print("LOGIN ERROR: $e");
      return null;
    }
  }

  // =========================
  // REGISTER
  // =========================
  Future<String?> register(String name, String email, String password) async {
    try {
      final res = await dio.post(
        "${Constants.baseUrl}/register",
        data: {"name": name, "email": email, "password": password},
      );

      print("REGISTER RESPONSE: ${res.data}");

      final token = res.data["token"] ?? res.data["jwt"];

      if (token != null && token.isNotEmpty) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } catch (e) {
      print("REGISTER ERROR: $e");
      return null;
    }
  }

  // =========================
  // GOOGLE LOGIN (FIXED)
  // =========================
  Future<String?> loginWithGoogle() async {
    try {
      final googleSignIn = GoogleSignIn();

      await googleSignIn.signOut();
      await _auth.signOut();

      final googleUser = await googleSignIn.signIn();

      if (googleUser == null) return null;

      final googleAuth = await googleUser.authentication;

      final credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      final userCredential = await _auth.signInWithCredential(credential);
      final user = userCredential.user;

      if (user == null) return null;

      final idToken = await user.getIdToken(true);

      if (idToken == null || idToken.isEmpty) return null;

      final res = await dio.post(
        "${Constants.baseUrl}/google",
        data: {"idToken": idToken},
      );

      final token = res.data["token"];

      if (token != null && token.isNotEmpty) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } on DioException catch (e) {
      print("GOOGLE LOGIN ERROR DATA: ${e.response?.data}");

      final message = e.response?.data["message"];

      if (message != null) {
        return "ERROR:$message";
      }

      return null;
    } catch (e) {
      print("GOOGLE LOGIN ERROR: $e");
      return null;
    }
  }

  Future<String?> loginWithFacebook() async {
    try {
      final result = await FacebookAuth.instance.login(
        permissions: ['email', 'public_profile'],
      );

      if (result.status != LoginStatus.success) {
        return null;
      }

      final accessToken = result.accessToken?.tokenString;

      if (accessToken == null) {
        return null;
      }

      final res = await dio.post(
        "${Constants.baseUrl}/facebook",
        data: {"accessToken": accessToken},
      );

      final token = res.data["token"];

      if (token != null) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } catch (e) {
      print("FACEBOOK LOGIN ERROR: $e");
      return null;
    }
  }

  Future<String?> registerWithGoogle() async {
    try {
      final googleSignIn = GoogleSignIn();

      await googleSignIn.signOut();
      await _auth.signOut();

      final googleUser = await googleSignIn.signIn();

      if (googleUser == null) return null;

      final googleAuth = await googleUser.authentication;

      final credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      final userCredential = await _auth.signInWithCredential(credential);
      final user = userCredential.user;

      if (user == null) return null;

      final idToken = await user.getIdToken(true);

      if (idToken == null || idToken.isEmpty) return null;

      final res = await dio.post(
        "${Constants.baseUrl}/google/register",
        data: {"idToken": idToken},
      );

      final token = res.data["token"];

      if (token != null && token.isNotEmpty) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } catch (e) {
      print("GOOGLE REGISTER ERROR: $e");
      return null;
    }
  }

  //

  Future<String?> registerWithFacebook() async {
    try {
      final result = await FacebookAuth.instance.login(permissions: ['email']);

      if (result.status != LoginStatus.success) {
        return null;
      }

      final accessToken = result.accessToken?.tokenString;

      final res = await dio.post(
        "${Constants.baseUrl}/facebook/register",
        data: {"accessToken": accessToken},
      );

      final token = res.data["token"];

      if (token != null && token.isNotEmpty) {
        await tokenService.saveToken(token);
        return token;
      }

      return null;
    } catch (e) {
      print(e);
      return null;
    }
  }

  // =========================
  // LOGOUT
  // =========================
  Future<void> logout() async {
    try {
      await _auth.signOut();
      await tokenService.clearToken();
    } catch (e) {
      print("LOGOUT ERROR: $e");
    }
  }
}
