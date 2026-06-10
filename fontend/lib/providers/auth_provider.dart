import 'package:flutter/material.dart';
import '../services/auth_service.dart';

class AuthProvider extends ChangeNotifier {
  final AuthService authService = AuthService();

  bool isLoading = false;

  Future<void> loginEmail(String email, String password) async {
    isLoading = true;
    notifyListeners();

    await authService.login(email, password);

    isLoading = false;
    notifyListeners();
  }

  Future<void> loginGoogle() async {
    isLoading = true;
    notifyListeners();

    await authService.loginWithGoogle();

    isLoading = false;
    notifyListeners();
  }
}