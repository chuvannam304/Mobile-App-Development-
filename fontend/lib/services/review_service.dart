import 'package:dio/dio.dart';

import '../models/product_review.dart';
import '../utils/constants.dart';
import 'package:image_picker/image_picker.dart';
import 'token_service.dart';

class ReviewService {
  final Dio dio = Dio();
  final TokenService tokenService = TokenService();

  Future<List<ProductReview>> getReviewsByProduct(String productId) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/reviews/product/$productId",
      );

      return (res.data as List).map((e) => ProductReview.fromJson(e)).toList();
    } catch (e) {
      print("GET REVIEWS ERROR: $e");
      return [];
    }
  }

  Future<bool> hasReviewed(String productId) async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        return false;
      }

      final res = await dio.get(
        "${Constants.apiUrl}/reviews/check/$productId",
        options: Options(headers: {"Authorization": "Bearer $token"}),
      );

      return res.data == true;
    } catch (e) {
      print("CHECK REVIEW ERROR: $e");
      return false;
    }
  }

  Future<Map<String, dynamic>> getReviewSummary(String productId) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/reviews/product/$productId/summary",
      );

      return {
        "averageRating":
            double.tryParse(res.data["averageRating"].toString()) ?? 0,
        "reviewCount": int.tryParse(res.data["reviewCount"].toString()) ?? 0,
      };
    } catch (e) {
      print("GET REVIEW SUMMARY ERROR: $e");
      return {"averageRating": 0.0, "reviewCount": 0};
    }
  }

  Future<bool> createReview({
    required String productId,
    required int rating,
    required String reviewText,
    required List<XFile> images,
  }) async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("CREATE REVIEW ERROR: TOKEN NULL");
        return false;
      }

      final formData = FormData();

      formData.fields.add(MapEntry("productId", productId));
      formData.fields.add(MapEntry("rating", rating.toString()));
      formData.fields.add(MapEntry("reviewText", reviewText));

      for (final image in images) {
        String fileName = image.name;

        if (fileName.isEmpty || !fileName.contains(".")) {
          fileName = "review_${DateTime.now().millisecondsSinceEpoch}.jpg";
        }

        formData.files.add(
          MapEntry(
            "images",
            await MultipartFile.fromFile(image.path, filename: fileName),
          ),
        );
      }

      await dio.post(
        "${Constants.apiUrl}/reviews",
        data: formData,
        options: Options(
          contentType: "multipart/form-data",
          headers: {"Authorization": "Bearer $token"},
        ),
      );

      return true;
    } on DioException catch (e) {
      print("CREATE REVIEW STATUS: ${e.response?.statusCode}");
      print("CREATE REVIEW DATA: ${e.response?.data}");
      print("CREATE REVIEW ERROR: ${e.message}");
      return false;
    } catch (e) {
      print("CREATE REVIEW ERROR: $e");
      return false;
    }
  }
}
