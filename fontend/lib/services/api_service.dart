import 'package:dio/dio.dart';
import '../utils/constants.dart';

class ApiService {
  final Dio dio = Dio(
    BaseOptions(
      baseUrl: Constants.baseUrl,
      headers: {"Content-Type": "application/json"},
    ),
  );
}