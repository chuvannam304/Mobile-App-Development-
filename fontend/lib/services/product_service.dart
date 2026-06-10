import 'package:dio/dio.dart';

import '../models/home_product.dart';
import '../utils/constants.dart';

class ProductService {
  final Dio dio = Dio();

  Future<List<HomeProduct>> getProductsByCategory(
    String categoryId,
  ) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/products/category/$categoryId",
      );

      return (res.data as List)
          .map(
            (e) => HomeProduct.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("PRODUCT CATEGORY ERROR: $e");
      return [];
    }
  }

  Future<List<HomeProduct>> getAllProductsByCategory(
    String categoryId,
  ) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/products/category/$categoryId/all",
      );

      return (res.data as List)
          .map(
            (e) => HomeProduct.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("ALL PRODUCT CATEGORY ERROR: $e");
      return [];
    }
  }
}