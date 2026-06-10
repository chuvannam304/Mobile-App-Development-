class ProductDetail {
  final String id;
  final String slug;
  final String productName;
  final String sku;
  final double salePrice;
  final double comparePrice;
  final int quantity;
  final String shortDescription;
  final String productDescription;
  final String imageUrl;

  ProductDetail({
    required this.id,
    required this.slug,
    required this.productName,
    required this.sku,
    required this.salePrice,
    required this.comparePrice,
    required this.quantity,
    required this.shortDescription,
    required this.productDescription,
    required this.imageUrl,
  });

  factory ProductDetail.fromJson(
    Map<String, dynamic> json,
  ) {
    return ProductDetail(
      id: json["id"] ?? "",
      slug: json["slug"] ?? "",
      productName: json["productName"] ?? "",
      sku: json["sku"] ?? "",
      salePrice: (json["salePrice"] ?? 0).toDouble(),
      comparePrice: (json["comparePrice"] ?? 0).toDouble(),
      quantity: json["quantity"] ?? 0,
      shortDescription: json["shortDescription"] ?? "",
      productDescription: json["productDescription"] ?? "",
      imageUrl: json["imageUrl"] ?? "",
    );
  }
}