import 'package:dio/dio.dart';

import '../models/bag_item.dart';
import '../utils/constants.dart';
import 'token_service.dart';

class BagService {
  final Dio dio = Dio();
  final TokenService tokenService = TokenService();

  Future<List<BagItem>> getMyBag() async {
    try {
      final token = await tokenService.getToken();

      if (token == null || token.isEmpty) {
        print("GET BAG ERROR: TOKEN NULL");
        return [];
      }

      final res = await dio.get(
        "${Constants.apiUrl}/cards/my-bag",
        options: Options(
          headers: {
            "Authorization": "Bearer $token",
          },
        ),
      );

      return (res.data as List)
          .map((e) => BagItem.fromJson(e))
          .toList();
    } catch (e) {
      print("GET BAG ERROR: $e");
      return [];
    }
  }
}