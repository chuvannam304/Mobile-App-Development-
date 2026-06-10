import 'package:dio/dio.dart';

import '../utils/constants.dart';
import 'token_service.dart';

class CardService {
  final Dio dio = Dio();
  final TokenService tokenService = TokenService();

  Future<bool> addToCart({
    required String productId,
    required String sizeText,
    required String colorText,
  }) async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("ADD TO CART ERROR: TOKEN NULL");
        return false;
      }

      await dio.post(
        "${Constants.apiUrl}/cards/add",
        data: {
          "productId": productId,
          "sizeText": sizeText,
          "colorText": colorText,
        },
        options: Options(headers: {"Authorization": "Bearer $token"}),
      );

      return true;
    } on DioException catch (e) {
      print("ADD TO CART STATUS: ${e.response?.statusCode}");
      print("ADD TO CART DATA: ${e.response?.data}");
      print("ADD TO CART ERROR: ${e.message}");
      return false;
    } catch (e) {
      print("ADD TO CART ERROR: $e");
      return false;
    }
  }
}
