class CategoryProduct {
  final String id;
  final String slug;
  final String productName;
  final double salePrice;
  final double comparePrice;
  final String shortDescription;
  final String imageUrl;

  CategoryProduct({
    required this.id,
    required this.slug,
    required this.productName,
    required this.salePrice,
    required this.comparePrice,
    required this.shortDescription,
    required this.imageUrl,
  });

  factory CategoryProduct.fromJson(Map<String, dynamic> json) {
    double toDouble(dynamic value) {
      if (value == null) return 0;
      if (value is num) return value.toDouble();
      return double.tryParse(value.toString()) ?? 0;
    }

    return CategoryProduct(
      id: json["id"]?.toString() ?? "",
      slug: json["slug"]?.toString() ?? "",
      productName: json["product_name"]?.toString() ??
          json["productName"]?.toString() ??
          "",
      salePrice: toDouble(json["sale_price"] ?? json["salePrice"]),
      comparePrice: toDouble(json["compare_price"] ?? json["comparePrice"]),
      shortDescription: json["short_description"]?.toString() ??
          json["shortDescription"]?.toString() ??
          "",
      imageUrl: json["image"]?.toString() ??
          json["image_url"]?.toString() ??
          json["imageUrl"]?.toString() ??
          "",
    );
  }
}