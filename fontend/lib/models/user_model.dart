class UserModel {
  final String? name;
  final String? email;
  final String? token;

  UserModel({this.name, this.email, this.token});

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      name: json["name"],
      email: json["email"],
      token: json["token"],
    );
  }
}