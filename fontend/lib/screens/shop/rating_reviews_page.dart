import 'package:flutter/material.dart';

import '../../models/product_review.dart';
import '../../services/review_service.dart';
import 'write_review_page.dart';

class RatingReviewsPage extends StatefulWidget {
  final String productId;

  const RatingReviewsPage({super.key, required this.productId});

  @override
  State<RatingReviewsPage> createState() => _RatingReviewsPageState();
}

class _RatingReviewsPageState extends State<RatingReviewsPage> {
  final ReviewService reviewService = ReviewService();

  late Future<List<ProductReview>> reviewsFuture;

  bool withPhotoOnly = false;
  bool hasReviewed = false;
  bool checkingReviewed = true;
  @override
  void initState() {
    super.initState();
    loadReviews();
    checkHasReviewed();
  }

  void loadReviews() {
    reviewsFuture = reviewService.getReviewsByProduct(widget.productId);
  }

  Future<void> checkHasReviewed() async {
    final result = await reviewService.hasReviewed(widget.productId);

    if (!mounted) return;

    setState(() {
      hasReviewed = result;
      checkingReviewed = false;
    });
  }

  Future<void> openWriteReview() async {
    if (hasReviewed) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Bạn đã đánh giá sản phẩm này rồi")),
      );
      return;
    }

    final result = await Navigator.push(
      context,
      PageRouteBuilder(
        opaque: false,
        pageBuilder: (_, __, ___) =>
            WriteReviewPage(productId: widget.productId),
      ),
    );

    if (result == true) {
      setState(() {
        loadReviews();
        hasReviewed = true;
      });
    }
  }

  String formatDate(DateTime? date) {
    if (date == null) return "";

    final months = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];

    return "${months[date.month - 1]} ${date.day}, ${date.year}";
  }

  double averageRating(List<ProductReview> reviews) {
    if (reviews.isEmpty) return 0;

    final total = reviews.fold<int>(0, (sum, review) => sum + review.rating);

    return total / reviews.length;
  }

  int countRating(List<ProductReview> reviews, int rating) {
    return reviews.where((r) => r.rating == rating).length;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),

      appBar: AppBar(
        backgroundColor: const Color(0xFFF9F9F9),
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
      ),

      floatingActionButton: FloatingActionButton.extended(
        backgroundColor: hasReviewed || checkingReviewed
            ? Colors.grey
            : const Color(0xFFDB3022),
        onPressed: hasReviewed || checkingReviewed ? null : openWriteReview,
        icon: const Icon(Icons.edit, color: Colors.white, size: 18),
        label: Text(
          checkingReviewed
              ? "Checking..."
              : hasReviewed
              ? "Bạn đã đánh giá rồi"
              : "Write a review",
          style: const TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),

      body: FutureBuilder<List<ProductReview>>(
        future: reviewsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: CircularProgressIndicator(color: Color(0xFFDB3022)),
            );
          }

          final allReviews = snapshot.data ?? [];

          final reviews = withPhotoOnly
              ? allReviews.where((r) => r.images.isNotEmpty).toList()
              : allReviews;

          final avg = averageRating(allReviews);

          final count5 = countRating(allReviews, 5);
          final count4 = countRating(allReviews, 4);
          final count3 = countRating(allReviews, 3);
          final count2 = countRating(allReviews, 2);
          final count1 = countRating(allReviews, 1);

          final maxCount = [
            count5,
            count4,
            count3,
            count2,
            count1,
            1,
          ].reduce((a, b) => a > b ? a : b);

          return SingleChildScrollView(
            padding: const EdgeInsets.fromLTRB(16, 10, 16, 90),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  "Rating&Reviews",
                  style: TextStyle(fontSize: 34, fontWeight: FontWeight.bold),
                ),

                const SizedBox(height: 28),

                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          avg.toStringAsFixed(1),
                          style: const TextStyle(
                            fontSize: 48,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Text(
                          "${allReviews.length} ratings",
                          style: const TextStyle(
                            color: Colors.grey,
                            fontSize: 14,
                          ),
                        ),
                      ],
                    ),

                    const SizedBox(width: 22),

                    Expanded(
                      child: Column(
                        children: [
                          _RatingBar(
                            stars: 5,
                            widthFactor: count5 / maxCount,
                            count: "$count5",
                          ),
                          _RatingBar(
                            stars: 4,
                            widthFactor: count4 / maxCount,
                            count: "$count4",
                          ),
                          _RatingBar(
                            stars: 3,
                            widthFactor: count3 / maxCount,
                            count: "$count3",
                          ),
                          _RatingBar(
                            stars: 2,
                            widthFactor: count2 / maxCount,
                            count: "$count2",
                          ),
                          _RatingBar(
                            stars: 1,
                            widthFactor: count1 / maxCount,
                            count: "$count1",
                          ),
                        ],
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 28),

                Row(
                  children: [
                    Text(
                      "${reviews.length} reviews",
                      style: const TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),

                    const Spacer(),

                    GestureDetector(
                      onTap: () {
                        setState(() {
                          withPhotoOnly = !withPhotoOnly;
                        });
                      },
                      child: Container(
                        width: 22,
                        height: 22,
                        decoration: BoxDecoration(
                          color: withPhotoOnly
                              ? const Color(0xFFDB3022)
                              : Colors.transparent,
                          border: Border.all(
                            color: withPhotoOnly
                                ? const Color(0xFFDB3022)
                                : Colors.grey,
                          ),
                          borderRadius: BorderRadius.circular(4),
                        ),
                        child: withPhotoOnly
                            ? const Icon(
                                Icons.check,
                                size: 16,
                                color: Colors.white,
                              )
                            : null,
                      ),
                    ),

                    const SizedBox(width: 10),

                    const Text("With photo"),
                  ],
                ),

                const SizedBox(height: 28),

                if (reviews.isEmpty)
                  const Center(
                    child: Padding(
                      padding: EdgeInsets.only(top: 40),
                      child: Text(
                        "No reviews yet",
                        style: TextStyle(color: Colors.grey),
                      ),
                    ),
                  )
                else
                  ...reviews.map(
                    (review) => Padding(
                      padding: const EdgeInsets.only(bottom: 22),
                      child: _ReviewCard(
                        name: review.userName,
                        date: formatDate(review.createdAt),
                        rating: review.rating,
                        text: review.reviewText,
                        images: review.images,
                        helpfulCount: review.helpfulCount,
                      ),
                    ),
                  ),
              ],
            ),
          );
        },
      ),
    );
  }
}

class _RatingBar extends StatelessWidget {
  final int stars;
  final double widthFactor;
  final String count;

  const _RatingBar({
    required this.stars,
    required this.widthFactor,
    required this.count,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        SizedBox(
          width: 70,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: List.generate(
              stars,
              (_) => const Icon(Icons.star, size: 14, color: Color(0xFFFFBA49)),
            ),
          ),
        ),

        const SizedBox(width: 8),

        Expanded(
          child: Align(
            alignment: Alignment.centerLeft,
            child: FractionallySizedBox(
              widthFactor: widthFactor == 0 ? 0.02 : widthFactor,
              child: Container(
                height: 8,
                decoration: BoxDecoration(
                  color: const Color(0xFFDB3022),
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
          ),
        ),

        const SizedBox(width: 8),

        SizedBox(
          width: 18,
          child: Text(
            count,
            style: const TextStyle(fontSize: 12, color: Colors.grey),
          ),
        ),
      ],
    );
  }
}

class _ReviewCard extends StatelessWidget {
  final String name;
  final String date;
  final int rating;
  final String text;
  final List<String> images;
  final int helpfulCount;

  const _ReviewCard({
    required this.name,
    required this.date,
    required this.rating,
    required this.text,
    required this.images,
    required this.helpfulCount,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      clipBehavior: Clip.none,
      children: [
        Container(
          margin: const EdgeInsets.only(left: 18),
          padding: const EdgeInsets.fromLTRB(34, 18, 16, 16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(8),
            boxShadow: [
              BoxShadow(color: Colors.black.withOpacity(0.06), blurRadius: 14),
            ],
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(name, style: const TextStyle(fontWeight: FontWeight.bold)),

              const SizedBox(height: 6),

              Row(
                children: [
                  ...List.generate(
                    5,
                    (index) => Icon(
                      index < rating ? Icons.star : Icons.star_border,
                      size: 16,
                      color: const Color(0xFFFFBA49),
                    ),
                  ),

                  const Spacer(),

                  Text(
                    date,
                    style: const TextStyle(color: Colors.grey, fontSize: 12),
                  ),
                ],
              ),

              const SizedBox(height: 12),

              Text(
                text,
                style: const TextStyle(
                  fontSize: 14,
                  height: 1.35,
                  color: Colors.black54,
                ),
              ),

              if (images.isNotEmpty) ...[
                const SizedBox(height: 12),

                SizedBox(
                  height: 90,
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: images.length,
                    itemBuilder: (context, index) {
                      final image = images[index];

                      return Padding(
                        padding: const EdgeInsets.only(right: 8),
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(6),
                          child: Image.network(
                            image.startsWith("http")
                                ? image
                                : "http://172.16.7.204:8080$image",
                            height: 90,
                            width: 90,
                            fit: BoxFit.cover,
                            errorBuilder: (_, __, ___) {
                              return Container(
                                height: 90,
                                width: 90,
                                color: Colors.grey.shade300,
                                child: const Icon(Icons.image),
                              );
                            },
                          ),
                        ),
                      );
                    },
                  ),
                ),
              ],
              const SizedBox(height: 12),

              Align(
                alignment: Alignment.centerRight,
                child: Text(
                  "Helpful  👍 $helpfulCount",
                  style: const TextStyle(color: Colors.grey, fontSize: 12),
                ),
              ),
            ],
          ),
        ),

        Positioned(
          top: -16,
          left: 0,
          child: CircleAvatar(
            radius: 20,
            backgroundColor: Colors.grey.shade300,
            child: const Icon(Icons.person, color: Colors.white),
          ),
        ),
      ],
    );
  }
}
