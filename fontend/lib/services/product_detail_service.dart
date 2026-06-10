import 'package:dio/dio.dart';

import '../models/product_detail.dart';
import '../utils/constants.dart';
import '../models/category_product.dart';

class ProductDetailService {
  final Dio dio = Dio();

  Future<ProductDetail?> getProduct(String productId) async {
    try {
      final res = await dio.get("${Constants.apiUrl}/products/$productId");

      return ProductDetail.fromJson(res.data);
    } catch (e) {
      print("PRODUCT DETAIL ERROR: $e");
      return null;
    }
  }

  Future<List<CategoryProduct>> getRelatedProducts(String productId) async {
    final res = await dio.get(
      "${Constants.apiUrl}/products/$productId/related",
    );

    return (res.data as List).map((e) => CategoryProduct.fromJson(e)).toList();
  }
}
