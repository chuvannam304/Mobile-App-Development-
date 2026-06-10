import 'package:dio/dio.dart';

import '../models/shop_category.dart';
import '../utils/constants.dart';

class ShopService {
  final Dio dio = Dio();

  // Women, Men, Kids
  Future<List<ShopCategory>> getParentCategories() async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/categories/parents",
      );

      return (res.data as List)
          .map(
            (e) => ShopCategory.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("PARENT CATEGORY ERROR: $e");
      return [];
    }
  }

  // New, Clothes, Shoes, Accessories
  Future<List<ShopCategory>> getChildrenCategories(
    String parentId,
  ) async {
    try {
      final res = await dio.get(
        "${Constants.apiUrl}/categories/$parentId/children",
      );

      return (res.data as List)
          .map(
            (e) => ShopCategory.fromJson(e),
          )
          .toList();
    } catch (e) {
      print("CHILD CATEGORY ERROR: $e");
      return [];
    }
  }
}