import 'package:dio/dio.dart';

import '../models/home_product.dart';
import '../utils/constants.dart';

class HomeService {
  final Dio dio = Dio();

  Future<Map<String, List<HomeProduct>>> getHomeProducts() async {
    try {
      final res = await dio.get("${Constants.apiUrl}/home");

      final newProductsJson = res.data["newProducts"] as List? ?? [];
      final saleProductsJson = res.data["saleProducts"] as List? ?? [];

      return {
        "newProducts": newProductsJson
            .map((item) => HomeProduct.fromJson(item))
            .toList(),

        "saleProducts": saleProductsJson
            .map((item) => HomeProduct.fromJson(item))
            .toList(),
      };
    } catch (e) {
      print("HOME SERVICE ERROR: $e");

      return {
        "newProducts": [],
        "saleProducts": [],
      };
    }
  }
}