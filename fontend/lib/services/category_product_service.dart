import 'package:dio/dio.dart';

import '../models/category_product.dart';
import '../utils/constants.dart';

class CategoryProductService {
  final Dio dio = Dio();

  Future<List<CategoryProduct>> getProductsByCategory(
    String categoryId,
  ) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/categories/$categoryId/products",
      );

      return (res.data as List)
          .map(
            (e) => CategoryProduct.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("CATEGORY PRODUCT ERROR: $e");
      return [];
    }
  }

  Future<List<CategoryProduct>> getAllProductsByCategory(
    String categoryId,
  ) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/products/category/$categoryId/all",
      );

      return (res.data as List)
          .map(
            (e) => CategoryProduct.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("ALL CATEGORY PRODUCT ERROR: $e");
      return [];
    }
  }
}