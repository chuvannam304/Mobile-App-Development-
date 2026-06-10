class HomeProduct {
  final String id;
  final String slug;
  final String productName;
  final double salePrice;
  final double comparePrice;
  final String shortDescription;
  final String imageUrl;

  HomeProduct({
    required this.id,
    required this.slug,
    required this.productName,
    required this.salePrice,
    required this.comparePrice,
    required this.shortDescription,
    required this.imageUrl,
  });

  factory HomeProduct.fromJson(Map<String, dynamic> json) {
    return HomeProduct(
      id: json["id"] ?? "",
      slug: json["slug"] ?? "",
      productName: json["productName"] ?? "",
      salePrice: (json["salePrice"] ?? 0).toDouble(),
      comparePrice: (json["comparePrice"] ?? 0).toDouble(),
      shortDescription: json["shortDescription"] ?? "",
      imageUrl: json["imageUrl"] ?? "",
    );
  }
}