class BagItem {
  final String itemId;
  final String productId;
  final String productName;
  final String imageUrl;
  final double price;
  final int quantity;
  final String sizeText;
  final String colorText;

  BagItem({
    required this.itemId,
    required this.productId,
    required this.productName,
    required this.imageUrl,
    required this.price,
    required this.quantity,
    required this.sizeText,
    required this.colorText,
  });

  factory BagItem.fromJson(Map<String, dynamic> json) {
    return BagItem(
      itemId: json["itemId"]?.toString() ?? "",
      productId: json["productId"]?.toString() ?? "",
      productName: json["productName"]?.toString() ?? "",
      imageUrl: json["imageUrl"]?.toString() ?? "",
      price: double.tryParse(json["price"].toString()) ?? 0,
      quantity: int.tryParse(json["quantity"].toString()) ?? 1,
      sizeText: json["sizeText"]?.toString() ?? "",
      colorText: json["colorText"]?.toString() ?? "",
    );
  }
}