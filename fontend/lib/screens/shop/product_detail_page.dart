import 'package:flutter/material.dart';

import '../../models/product_detail.dart';
import '../../services/product_detail_service.dart';
import '../../models/category_product.dart';
import 'rating_reviews_page.dart';
import '../../services/review_service.dart';
import '../../models/product_review.dart';
import '../../utils/constants.dart';
import '../../services/card_service.dart';
import '../bag/bag_page.dart';
import '../../services/favorite_service.dart';

class ProductDetailPage extends StatefulWidget {
  final String productId;

  //  const ProductDetailPage({super.key, required this.productId});
  final bool initialFavorite;

  const ProductDetailPage({
    super.key,
    required this.productId,
    this.initialFavorite = false,
  });
  @override
  State<ProductDetailPage> createState() => _ProductDetailPageState();
}

class _ProductDetailPageState extends State<ProductDetailPage> {
  final ProductDetailService service = ProductDetailService();
  final ReviewService reviewService = ReviewService();
  final CardService cardService = CardService();
  final FavoriteService favoriteService = FavoriteService();
  bool isFavorite = false;
  bool isAddingToCart = false;
  double averageRating = 0;
  int reviewCount = 0;
  List<ProductReview> reviews = [];

  final ScrollController _scrollController = ScrollController();

  ProductDetail? product;
  bool isLoading = true;
  bool showBottomAdd = false;
  String selectedSize = "Size";
  String selectedColor = "Color";

  @override
  void initState() {
    super.initState();

    showBottomAdd = true;

    loadProduct();
    loadReviewSummary();
    checkProductFavorite();
    isFavorite = widget.initialFavorite;
    _scrollController.addListener(() {
      if (_scrollController.offset > 260) {
        if (showBottomAdd) {
          setState(() {
            showBottomAdd = false;
          });
        }
      } else {
        if (!showBottomAdd) {
          setState(() {
            showBottomAdd = true;
          });
        }
      }
    });
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  Future<void> checkProductFavorite() async {
    final exists = await favoriteService.isProductFavorite(widget.productId);

    if (!mounted) return;

    setState(() {
      isFavorite = exists;
    });
  }

  Future<void> loadProduct() async {
    final data = await service.getProduct(widget.productId);

    if (!mounted) return;

    setState(() {
      product = data;
      isLoading = false;
    });
  }

  Future<void> addToFavorite() async {
    if (selectedSize == "Size") {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Please select size")));
      return;
    }

    if (selectedColor == "Color") {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Please select color")));
      return;
    }

    bool success;

    if (isFavorite) {
      success = await favoriteService.removeFromFavorite(
        productId: widget.productId,
        sizeText: selectedSize,
        colorText: selectedColor,
      );

      if (success) {
        setState(() {
          isFavorite = false;
        });
      }
    } else {
      success = await favoriteService.addToFavorite(
        productId: widget.productId,
        sizeText: selectedSize,
        colorText: selectedColor,
      );

      if (success) {
        setState(() {
          isFavorite = true;
        });
      }
    }
  }

  Future<void> addToCart() async {
    if (selectedSize == "Size") {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Please select size")));
      return;
    }

    if (selectedColor == "Color") {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Please select color")));
      return;
    }

    setState(() {
      isAddingToCart = true;
    });

    final success = await cardService.addToCart(
      productId: widget.productId,
      sizeText: selectedSize,
      colorText: selectedColor,
    );

    if (!mounted) return;

    setState(() {
      isAddingToCart = false;
    });

    if (success) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (_) => const BagPage()),
      );
    } else {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Add to cart failed")));
    }
  }

  Future<void> loadReviewSummary() async {
    final data = await reviewService.getReviewSummary(widget.productId);
    final reviewData = await reviewService.getReviewsByProduct(
      widget.productId,
    );

    if (!mounted) return;

    setState(() {
      averageRating = data["averageRating"] ?? 0;
      reviewCount = data["reviewCount"] ?? 0;
      reviews = reviewData;
    });
  }

  void _showSizeSheet(BuildContext context) {
    final sizes = ["XS", "S", "M", "L", "XL"];

    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(28)),
      ),
      builder: (_) {
        return Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Container(
                width: 60,
                height: 5,
                decoration: BoxDecoration(
                  color: Colors.grey.shade400,
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
              const SizedBox(height: 18),
              const Text(
                "Select size",
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              Wrap(
                spacing: 16,
                runSpacing: 14,
                children: sizes.map((size) {
                  return GestureDetector(
                    onTap: () {
                      setState(() {
                        selectedSize = size;
                      });
                      Navigator.pop(context);
                      checkFavoriteStatus();
                    },
                    child: Container(
                      width: 90,
                      height: 44,
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                        color: selectedSize == size
                            ? const Color(0xFFDB3022)
                            : Colors.white,
                        border: Border.all(color: const Color(0xFFE0E0E0)),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Text(
                        size,
                        style: TextStyle(
                          color: selectedSize == size
                              ? Colors.white
                              : Colors.black,
                        ),
                      ),
                    ),
                  );
                }).toList(),
              ),
              const SizedBox(height: 18),
              _InfoRow(title: "Size info"),
              const SizedBox(height: 18),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                    addToCart();
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFFDB3022),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                  ),
                  child: const Text(
                    "ADD TO CART",
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
            ],
          ),
        );
      },
    );
  }

  void _showColorSheet(BuildContext context) {
    final colors = ["Black", "White", "Yellow", "Blue", "Red"];

    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(28)),
      ),
      builder: (_) {
        return Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Container(
                width: 60,
                height: 5,
                decoration: BoxDecoration(
                  color: Colors.grey.shade400,
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
              const SizedBox(height: 18),
              const Text(
                "Select color",
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              Wrap(
                spacing: 16,
                runSpacing: 14,
                children: colors.map((color) {
                  return GestureDetector(
                    onTap: () {
                      setState(() {
                        selectedColor = color;
                      });
                      Navigator.pop(context);
                      checkFavoriteStatus();
                    },
                    child: Container(
                      width: 96,
                      height: 44,
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                        color: selectedColor == color
                            ? const Color(0xFFDB3022)
                            : Colors.white,
                        border: Border.all(color: const Color(0xFFE0E0E0)),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Text(
                        color,
                        style: TextStyle(
                          color: selectedColor == color
                              ? Colors.white
                              : Colors.black,
                        ),
                      ),
                    ),
                  );
                }).toList(),
              ),
              const SizedBox(height: 20),
            ],
          ),
        );
      },
    );
  }

  String fullImageUrl(String image) {
    if (image.startsWith("http")) return image;

    final base = Constants.apiUrl.replaceAll("/api", "");
    return "$base$image";
  }

  Future<void> checkFavoriteStatus() async {
    if (selectedSize == "Size" || selectedColor == "Color") return;

    final exists = await favoriteService.checkFavorite(
      productId: widget.productId,
      sizeText: selectedSize,
      colorText: selectedColor,
    );

    if (!mounted) return;

    setState(() {
      isFavorite = exists;
    });
  }

  Future<void> openReviewsPage() async {
    await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => RatingReviewsPage(productId: widget.productId),
      ),
    );

    loadReviewSummary();
  }

  @override
  Widget build(BuildContext context) {
    if (isLoading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(color: Color(0xFFDB3022)),
        ),
      );
    }

    if (product == null) {
      return const Scaffold(body: Center(child: Text("Product not found")));
    }

    final p = product!;

    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        centerTitle: true,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: Text(
          p.productName,
          style: const TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.bold,
          ),
        ),
        actions: const [
          Padding(
            padding: EdgeInsets.only(right: 16),
            child: Icon(Icons.share, color: Colors.black),
          ),
        ],
      ),
      body: Stack(
        children: [
          SingleChildScrollView(
            controller: _scrollController,
            padding: EdgeInsets.only(bottom: showBottomAdd ? 80 : 0),
            child: Column(
              children: [
                SizedBox(
                  height: 420,
                  width: double.infinity,
                  child: p.imageUrl.isNotEmpty
                      ? Image.asset(
                          "assets/images/${p.imageUrl}",
                          fit: BoxFit.cover,
                        )
                      : Container(
                          color: Colors.grey.shade300,
                          child: const Icon(Icons.image, size: 60),
                        ),
                ),
                Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    children: [
                      Row(
                        children: [
                          _SelectBox(
                            text: selectedSize,
                            onTap: () {
                              _showSizeSheet(context);
                            },
                          ),
                          const SizedBox(width: 12),
                          _SelectBox(
                            text: selectedColor,
                            onTap: () {
                              _showColorSheet(context);
                            },
                          ),
                          const Spacer(),
                          GestureDetector(
                            onTap: addToFavorite,
                            child: Container(
                              width: 42,
                              height: 42,
                              decoration: BoxDecoration(
                                color: Colors.white,
                                shape: BoxShape.circle,
                                boxShadow: [
                                  BoxShadow(
                                    color: Colors.black.withOpacity(0.12),
                                    blurRadius: 8,
                                  ),
                                ],
                              ),
                              child: Icon(
                                isFavorite
                                    ? Icons.favorite
                                    : Icons.favorite_border,
                                color: isFavorite ? Colors.red : Colors.grey,
                              ),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 22),
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Expanded(
                            child: Text(
                              p.productName,
                              style: const TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          Text(
                            "\$${p.salePrice.toStringAsFixed(0)}",
                            style: const TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 4),
                      Align(
                        alignment: Alignment.centerLeft,
                        child: Text(
                          p.shortDescription,
                          style: const TextStyle(
                            color: Colors.grey,
                            fontSize: 12,
                          ),
                        ),
                      ),
                      const SizedBox(height: 8),

                      GestureDetector(
                        onTap: openReviewsPage,
                        child: Row(
                          children: [
                            ...List.generate(
                              5,
                              (index) => Icon(
                                index < averageRating.round()
                                    ? Icons.star
                                    : Icons.star_border,
                                size: 18,
                                color: const Color(0xFFFFBA49),
                              ),
                            ),
                            const SizedBox(width: 6),
                            Text(
                              reviewCount == 0
                                  ? "No reviews yet"
                                  : "($reviewCount reviews)",
                              style: const TextStyle(
                                color: Colors.grey,
                                fontSize: 12,
                              ),
                            ),
                          ],
                        ),
                      ),

                      if (reviews.isNotEmpty) ...[
                        const SizedBox(height: 12),
                        SizedBox(
                          height: 230,
                          child: ListView.separated(
                            itemCount: reviews.length,
                            separatorBuilder: (_, __) =>
                                const SizedBox(height: 10),
                            itemBuilder: (context, index) {
                              final review = reviews[index];

                              return GestureDetector(
                                onTap: openReviewsPage,
                                child: Container(
                                  width: double.infinity,
                                  padding: const EdgeInsets.all(12),
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(10),
                                    border: Border.all(
                                      color: const Color(0xFFE0E0E0),
                                    ),
                                  ),
                                  child: Row(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      CircleAvatar(
                                        radius: 18,
                                        backgroundColor: Colors.grey.shade300,
                                        child: const Icon(
                                          Icons.person,
                                          color: Colors.white,
                                          size: 20,
                                        ),
                                      ),
                                      const SizedBox(width: 10),
                                      Expanded(
                                        child: Column(
                                          crossAxisAlignment:
                                              CrossAxisAlignment.start,
                                          children: [
                                            Row(
                                              children: [
                                                Expanded(
                                                  child: Text(
                                                    review.userName,
                                                    maxLines: 1,
                                                    overflow:
                                                        TextOverflow.ellipsis,
                                                    style: const TextStyle(
                                                      fontWeight:
                                                          FontWeight.bold,
                                                      fontSize: 13,
                                                    ),
                                                  ),
                                                ),
                                                Row(
                                                  children: List.generate(
                                                    5,
                                                    (starIndex) => Icon(
                                                      starIndex < review.rating
                                                          ? Icons.star
                                                          : Icons.star_border,
                                                      size: 13,
                                                      color: const Color(
                                                        0xFFFFBA49,
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                              ],
                                            ),
                                            const SizedBox(height: 5),
                                            Text(
                                              review.reviewText,
                                              maxLines: 2,
                                              overflow: TextOverflow.ellipsis,
                                              style: const TextStyle(
                                                fontSize: 13,
                                                height: 1.3,
                                                color: Colors.black54,
                                              ),
                                            ),
                                            if (review.images.isNotEmpty) ...[
                                              const SizedBox(height: 8),

                                              SizedBox(
                                                height: 58,

                                                child: ListView.builder(
                                                  scrollDirection:
                                                      Axis.horizontal,

                                                  itemCount:
                                                      review.images.length,

                                                  itemBuilder: (context, imgIndex) {
                                                    return Padding(
                                                      padding:
                                                          const EdgeInsets.only(
                                                            right: 6,
                                                          ),

                                                      child: ClipRRect(
                                                        borderRadius:
                                                            BorderRadius.circular(
                                                              6,
                                                            ),

                                                        child: Image.network(
                                                          fullImageUrl(
                                                            review
                                                                .images[imgIndex],
                                                          ),
                                                          height: 58,
                                                          width: 58,
                                                          fit: BoxFit.cover,

                                                          errorBuilder:
                                                              (_, __, ___) {
                                                                return Container(
                                                                  height: 58,
                                                                  width: 58,
                                                                  color: Colors
                                                                      .grey
                                                                      .shade300,

                                                                  child: const Icon(
                                                                    Icons.image,
                                                                    size: 18,
                                                                  ),
                                                                );
                                                              },
                                                        ),
                                                      ),
                                                    );
                                                  },
                                                ),
                                              ),
                                            ],
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              );
                            },
                          ),
                        ),
                      ],

                      const SizedBox(height: 16),
                      Text(
                        p.productDescription,
                        style: const TextStyle(
                          fontSize: 14,
                          height: 1.4,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 20),
                      _InfoRow(title: "Shipping info"),
                      _InfoRow(title: "Support"),
                      const SizedBox(height: 24),
                      _RelatedProducts(
                        service: service,
                        productId: widget.productId,
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),

          if (showBottomAdd)
            Positioned(
              left: 16,
              right: 16,
              bottom: 16,
              child: SizedBox(
                height: 48,
                child: ElevatedButton(
                  onPressed: isAddingToCart ? null : addToCart,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFFDB3022),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                    elevation: 6,
                  ),
                  child: Text(
                    isAddingToCart ? "ADDING..." : "ADD TO CART",
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }
}

class _SelectBox extends StatelessWidget {
  final String text;
  final VoidCallback? onTap;

  const _SelectBox({required this.text, this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      behavior: HitTestBehavior.opaque,
      onTap: onTap,
      child: Container(
        width: 138,
        height: 40,
        padding: const EdgeInsets.symmetric(horizontal: 12),
        decoration: BoxDecoration(
          color: Colors.white,
          border: Border.all(color: const Color(0xFFE0E0E0)),
          borderRadius: BorderRadius.circular(8),
        ),
        child: Row(
          children: [
            Text(text),
            const Spacer(),
            const Icon(Icons.keyboard_arrow_down),
          ],
        ),
      ),
    );
  }
}

class _InfoRow extends StatelessWidget {
  final String title;

  const _InfoRow({required this.title});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 48,
      decoration: const BoxDecoration(
        border: Border(top: BorderSide(color: Color(0xFFE0E0E0))),
      ),
      child: Row(
        children: [
          Text(title, style: const TextStyle(fontSize: 16)),
          const Spacer(),
          const Icon(Icons.keyboard_arrow_right),
        ],
      ),
    );
  }
}

class _RelatedProducts extends StatelessWidget {
  final ProductDetailService service;
  final String productId;

  const _RelatedProducts({required this.service, required this.productId});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<CategoryProduct>>(
      future: service.getRelatedProducts(productId),
      builder: (context, snapshot) {
        final products = snapshot.data ?? [];

        if (products.isEmpty) return const SizedBox();

        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Text(
                  "You can also like this",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                const Spacer(),
                Text(
                  "${products.length} items",
                  style: const TextStyle(fontSize: 12, color: Colors.grey),
                ),
              ],
            ),
            const SizedBox(height: 12),
            SizedBox(
              height: 245,
              child: ListView.separated(
                scrollDirection: Axis.horizontal,
                itemCount: products.length,
                separatorBuilder: (_, __) => const SizedBox(width: 14),
                itemBuilder: (context, index) {
                  final p = products[index];

                  return GestureDetector(
                    onTap: () {
                      Navigator.pushReplacement(
                        context,
                        MaterialPageRoute(
                          builder: (_) => ProductDetailPage(productId: p.id),
                        ),
                      );
                    },
                    child: SizedBox(
                      width: 140,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Image.asset(
                            "assets/images/${p.imageUrl}",
                            height: 170,
                            width: 140,
                            fit: BoxFit.cover,
                            errorBuilder: (_, __, ___) {
                              return Container(
                                height: 170,
                                width: 140,
                                color: Colors.grey.shade300,
                                child: const Icon(Icons.image),
                              );
                            },
                          ),
                          const SizedBox(height: 6),
                          Text(
                            p.productName,
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                            style: const TextStyle(fontWeight: FontWeight.bold),
                          ),
                          Text(
                            "\$${p.salePrice.toStringAsFixed(0)}",
                            style: const TextStyle(
                              color: Color(0xFFDB3022),
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        );
      },
    );
  }
}
