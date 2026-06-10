class ShopCategory {
  final String id;
  final String categoryName;
  final String image;

  ShopCategory({
    required this.id,
    required this.categoryName,
    required this.image,
  });

  factory ShopCategory.fromJson(Map<String, dynamic> json) {
    return ShopCategory(
      id: json["id"] ?? "",
      categoryName: json["categoryName"] ?? "",
      image: json["image"] ?? "",
    );
  }
}