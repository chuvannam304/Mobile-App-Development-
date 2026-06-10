class ProductReview {
  final String id;
  final String productId;
  final String userName;
  final int rating;
  final String reviewText;
  final List<String> images;
  final int helpfulCount;
  final DateTime? createdAt;

  ProductReview({
    required this.id,
    required this.productId,
    required this.userName,
    required this.rating,
    required this.reviewText,
    required this.images,
    required this.helpfulCount,
    required this.createdAt,
  });

  factory ProductReview.fromJson(Map<String, dynamic> json) {

    List<String> images = [];

    if (json["images"] != null) {
      images = List<String>.from(
        json["images"].map((e) => e.toString()),
      );
    }

    return ProductReview(
      id: json["id"]?.toString() ?? "",

      productId:
          json["product_id"]?.toString() ??
          json["productId"]?.toString() ??
          "",

      userName:
          json["user_name"]?.toString() ??
          json["userName"]?.toString() ??
          "",

      rating: json["rating"] ?? 0,

      reviewText:
          json["review_text"]?.toString() ??
          json["reviewText"]?.toString() ??
          "",

      images: images,

      helpfulCount:
          json["helpful_count"] ??
          json["helpfulCount"] ??
          0,

      createdAt: json["created_at"] != null
          ? DateTime.tryParse(
              json["created_at"].toString(),
            )
          : json["createdAt"] != null
              ? DateTime.tryParse(
                  json["createdAt"].toString(),
                )
              : null,
    );
  }
}