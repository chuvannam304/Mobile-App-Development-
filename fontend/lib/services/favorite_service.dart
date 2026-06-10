import 'package:dio/dio.dart';

import '../utils/constants.dart';
import 'token_service.dart';

class FavoriteService {
  final Dio dio = Dio();
  final TokenService tokenService = TokenService();

  Future<bool> addToFavorite({
    required String productId,
    required String sizeText,
    required String colorText,
  }) async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("ADD FAVORITE ERROR: TOKEN NULL");
        return false;
      }

      await dio.post(
        "${Constants.apiUrl}/favorites",
        data: {
          "productId": productId,
          "sizeText": sizeText,
          "colorText": colorText,
        },
        options: Options(headers: {"Authorization": "Bearer $token"}),
      );

      return true;
    } on DioException catch (e) {
      print("ADD FAVORITE STATUS: ${e.response?.statusCode}");
      print("ADD FAVORITE DATA: ${e.response?.data}");
      return false;
    } catch (e) {
      print("ADD FAVORITE ERROR: $e");
      return false;
    }
  }

  Future<bool> removeFromFavorite({
    required String productId,
    required String sizeText,
    required String colorText,
  }) async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("REMOVE FAVORITE ERROR: TOKEN NULL");
        return false;
      }

      await dio.delete(
        "${Constants.apiUrl}/favorites",
        data: {
          "productId": productId,
          "sizeText": sizeText,
          "colorText": colorText,
        },
        options: Options(headers: {"Authorization": "Bearer $token"}),
      );

      return true;
    } on DioException catch (e) {
      print("REMOVE FAVORITE STATUS: ${e.response?.statusCode}");
      print("REMOVE FAVORITE DATA: ${e.response?.data}");
      return false;
    } catch (e) {
      print("REMOVE FAVORITE ERROR: $e");
      return false;
    }
  }

  Future<bool> checkFavorite({
    required String productId,
    required String sizeText,
    required String colorText,
  }) async {
    final favorites = await getFavorites();

    return favorites.any((item) {
      final product = item["product"] ?? {};
      return product["id"].toString() == productId &&
          item["sizeText"].toString() == sizeText &&
          item["colorText"].toString() == colorText;
    });
  }

  Future<bool> isProductFavorite(String productId) async {
    final favorites = await getFavorites();

    return favorites.any((item) {
      final product = item["product"] ?? {};
      return product["id"].toString() == productId;
    });
  }

  Future<List<dynamic>> getFavorites() async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("GET FAVORITES ERROR: TOKEN NULL");
        return [];
      }

      final res = await dio.get(
        "${Constants.apiUrl}/favorites",
        options: Options(headers: {"Authorization": "Bearer $token"}),
      );

      if (res.data is List) {
        return res.data;
      }

      return [];
    } on DioException catch (e) {
      print("GET FAVORITES STATUS: ${e.response?.statusCode}");
      print("GET FAVORITES DATA: ${e.response?.data}");
      return [];
    } catch (e) {
      print("GET FAVORITES ERROR: $e");
      return [];
    }
  }
}
