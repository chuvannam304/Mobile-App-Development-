import 'dart:async';
import 'package:flutter/material.dart';

import '../../models/home_product.dart';
import '../../services/home_service.dart';
import '../shop/product_detail_page.dart';
import '../../services/favorite_service.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final HomeService homeService = HomeService();

  late Future<Map<String, List<HomeProduct>>> homeFuture;

  final PageController _pageController = PageController();

  int _currentPage = 0;

  final List<String> bannerImages = [
    "assets/images/slidershow1.jpg",
    "assets/images/slidershow2.jpg",
  ];

  Timer? _timer;

  @override
  void initState() {
    super.initState();
    homeFuture = homeService.getHomeProducts();

    _timer = Timer.periodic(const Duration(seconds: 3), (timer) {
      if (_pageController.hasClients) {
        _currentPage++;

        if (_currentPage >= bannerImages.length) {
          _currentPage = 0;
        }

        _pageController.animateToPage(
          _currentPage,
          duration: const Duration(milliseconds: 500),
          curve: Curves.easeInOut,
        );
      }
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    _pageController.dispose();
    super.dispose();
  }

  Future<void> refreshHome() async {
    setState(() {
      homeFuture = homeService.getHomeProducts();
    });

    await homeFuture;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),

      body: SafeArea(
        child: RefreshIndicator(
          onRefresh: refreshHome,
          child: FutureBuilder<Map<String, List<HomeProduct>>>(
            future: homeFuture,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const Center(
                  child: CircularProgressIndicator(color: Color(0xFFDB3022)),
                );
              }

              final newProducts = snapshot.data?["newProducts"] ?? [];
              final saleProducts = snapshot.data?["saleProducts"] ?? [];

              return SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    _buildHeader(),

                    const SizedBox(height: 28),

                    _SectionHeader(
                      title: "Sale",
                      subtitle: "Super summer sale",
                      onViewAll: () {},
                    ),

                    const SizedBox(height: 14),

                    _ProductHorizontalList(
                      products: saleProducts,
                      badgeText: "-20%",
                    ),

                    const SizedBox(height: 30),

                    _SectionHeader(
                      title: "New",
                      subtitle: "You've never seen it before!",
                      onViewAll: () {},
                    ),

                    const SizedBox(height: 14),

                    _ProductHorizontalList(
                      products: newProducts,
                      badgeText: "NEW",
                    ),

                    const SizedBox(height: 30),

                    const _FashionPromoGrid(),

                    const SizedBox(height: 24),
                  ],
                ),
              );
            },
          ),
        ),
      ),
    );
  }

  Widget _buildHeader() {
    return Stack(
      children: [
        SizedBox(
          height: 360,
          width: double.infinity,
          child: PageView.builder(
            controller: _pageController,
            itemCount: bannerImages.length,
            onPageChanged: (index) {
              setState(() {
                _currentPage = index;
              });
            },
            itemBuilder: (context, index) {
              return Container(
                decoration: BoxDecoration(
                  color: Colors.black,
                  image: DecorationImage(
                    image: AssetImage(bannerImages[index]),
                    fit: BoxFit.cover,
                  ),
                ),
              );
            },
          ),
        ),

        Container(
          height: 360,
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                Colors.black.withOpacity(0.05),
                Colors.black.withOpacity(0.65),
              ],
            ),
          ),
        ),

        Positioned(
          left: 18,
          right: 18,
          bottom: 32,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                "Fashion\nsale",
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 48,
                  height: 0.95,
                  fontWeight: FontWeight.bold,
                ),
              ),

              const SizedBox(height: 18),

              SizedBox(
                width: 160,
                height: 44,
                child: ElevatedButton(
                  onPressed: () {},
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFFDB3022),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                    elevation: 0,
                  ),
                  child: const Text(
                    "Check",
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),

        Positioned(
          bottom: 10,
          left: 0,
          right: 0,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: List.generate(
              bannerImages.length,
              (index) => Container(
                margin: const EdgeInsets.symmetric(horizontal: 4),
                width: _currentPage == index ? 10 : 8,
                height: _currentPage == index ? 10 : 8,
                decoration: BoxDecoration(
                  color: _currentPage == index ? Colors.white : Colors.white54,
                  shape: BoxShape.circle,
                ),
              ),
            ),
          ),
        ),

        Positioned(
          top: 14,
          right: 14,
          child: Container(
            width: 38,
            height: 38,
            decoration: BoxDecoration(
              color: Colors.white.withOpacity(0.9),
              shape: BoxShape.circle,
            ),
            child: const Icon(Icons.search, color: Colors.black, size: 22),
          ),
        ),
      ],
    );
  }
}

class _SectionHeader extends StatelessWidget {
  final String title;
  final String subtitle;
  final VoidCallback onViewAll;

  const _SectionHeader({
    required this.title,
    required this.subtitle,
    required this.onViewAll,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),

      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,

        crossAxisAlignment: CrossAxisAlignment.end,

        children: [
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,

            children: [
              Text(
                title,
                style: const TextStyle(
                  fontSize: 34,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),

              const SizedBox(height: 4),

              Text(
                subtitle,
                style: const TextStyle(fontSize: 12, color: Colors.grey),
              ),
            ],
          ),

          GestureDetector(
            onTap: onViewAll,
            child: const Text(
              "View all",
              style: TextStyle(fontSize: 12, color: Colors.black),
            ),
          ),
        ],
      ),
    );
  }
}

class _ProductHorizontalList extends StatelessWidget {
  final List<HomeProduct> products;
  final String badgeText;

  const _ProductHorizontalList({
    required this.products,
    required this.badgeText,
  });

  @override
  Widget build(BuildContext context) {
    if (products.isEmpty) {
      return const Padding(
        padding: EdgeInsets.symmetric(horizontal: 16),
        child: Text("No products", style: TextStyle(color: Colors.grey)),
      );
    }

    return SizedBox(
      height: 260,
      child: ListView.separated(
        padding: const EdgeInsets.symmetric(horizontal: 16),
        scrollDirection: Axis.horizontal,
        itemCount: products.length,
        separatorBuilder: (_, __) => const SizedBox(width: 14),
        itemBuilder: (context, index) {
          return _ProductCard(product: products[index], badgeText: badgeText);
        },
      ),
    );
  }
}

class _ProductCard extends StatefulWidget {
  final HomeProduct product;
  final String badgeText;

  const _ProductCard({required this.product, required this.badgeText});

  @override
  State<_ProductCard> createState() => _ProductCardState();
}

class _ProductCardState extends State<_ProductCard> {
  final FavoriteService favoriteService = FavoriteService();
  bool isFavorite = false;
  @override
  void initState() {
    super.initState();
    checkFavorite();
  }

  Future<void> checkFavorite() async {
    final exists = await favoriteService.isProductFavorite(widget.product.id);

    if (!mounted) return;

    setState(() {
      isFavorite = exists;
    });
  }

  Future<void> toggleFavorite() async {
    bool ok;

    if (isFavorite) {
      ok = await favoriteService.removeFromFavorite(
        productId: widget.product.id,
        sizeText: "S",
        colorText: "Black",
      );

      if (ok) {
        setState(() {
          isFavorite = false;
        });
      }
    } else {
      ok = await favoriteService.addToFavorite(
        productId: widget.product.id,
        sizeText: "S",
        colorText: "Black",
      );

      if (ok) {
        setState(() {
          isFavorite = true;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final product = widget.product;
    final badgeText = widget.badgeText;

    final hasComparePrice =
        product.comparePrice > product.salePrice && product.comparePrice > 0;

    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => ProductDetailPage(productId: product.id),
          ),
        );
      },
      child: SizedBox(
        width: 150,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Stack(
              children: [
                ClipRRect(
                  borderRadius: BorderRadius.circular(8),
                  child: Container(
                    width: 150,
                    height: 190,
                    color: const Color(0xFFEAEAEA),
                    child: product.imageUrl.isNotEmpty
                        ? Image.asset(
                            "assets/images/${product.imageUrl}",
                            fit: BoxFit.cover,
                            errorBuilder: (_, __, ___) {
                              return const _ProductImagePlaceholder();
                            },
                          )
                        : const _ProductImagePlaceholder(),
                  ),
                ),

                Positioned(
                  top: 8,
                  left: 8,
                  child: Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 8,
                      vertical: 4,
                    ),
                    decoration: BoxDecoration(
                      color: badgeText == "NEW"
                          ? Colors.black
                          : const Color(0xFFDB3022),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Text(
                      badgeText,
                      style: const TextStyle(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                  ),
                ),

                Positioned(
                  right: 0,
                  bottom: 0,
                  child: GestureDetector(
                    onTap: toggleFavorite,
                    child: Container(
                      width: 36,
                      height: 36,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        shape: BoxShape.circle,
                        boxShadow: [
                          BoxShadow(
                            color: Colors.black.withOpacity(0.12),
                            blurRadius: 8,
                            offset: const Offset(0, 2),
                          ),
                        ],
                      ),
                      child: Icon(
                        isFavorite ? Icons.favorite : Icons.favorite_border,
                        color: isFavorite
                            ? const Color(0xFFDB3022)
                            : Colors.grey,
                        size: 20,
                      ),
                    ),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 8),

            Row(
              children: List.generate(
                5,
                (index) =>
                    const Icon(Icons.star, size: 14, color: Color(0xFFFFBA49)),
              ),
            ),

            const SizedBox(height: 4),

            Text(
              product.productName,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
              style: const TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.w600,
                color: Colors.black,
              ),
            ),

            const SizedBox(height: 4),

            Row(
              children: [
                if (hasComparePrice)
                  Text(
                    "\$${product.comparePrice.toStringAsFixed(0)}",
                    style: const TextStyle(
                      fontSize: 12,
                      color: Colors.grey,
                      decoration: TextDecoration.lineThrough,
                    ),
                  ),

                if (hasComparePrice) const SizedBox(width: 6),

                Text(
                  "\$${product.salePrice.toStringAsFixed(0)}",
                  style: const TextStyle(
                    fontSize: 14,
                    color: Color(0xFFDB3022),
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

class _ProductImagePlaceholder extends StatelessWidget {
  const _ProductImagePlaceholder();

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Icon(Icons.image_outlined, color: Colors.grey, size: 42),
    );
  }
}

class _FashionPromoGrid extends StatelessWidget {
  const _FashionPromoGrid();

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        _PromoTile(
          height: 220,
          image: "assets/images/slidershow1.jpg",
          title: "New collection",
          alignment: Alignment.bottomCenter,
        ),

        Row(
          children: const [
            Expanded(child: _PromoTextBox(height: 150, title: "Summer\nsale")),
            Expanded(
              child: _PromoTile(
                height: 150,
                image: "assets/images/anh7.jpg",
                title: "Men’s\nhoodies",
                alignment: Alignment.centerRight,
              ),
            ),
          ],
        ),

        Row(
          children: const [
            Expanded(
              child: _PromoTile(
                height: 150,
                image: "assets/images/anh8.jpg",
                title: "Black",
                alignment: Alignment.bottomLeft,
              ),
            ),
            Expanded(
              child: _PromoTile(
                height: 150,
                image: "assets/images/mtops1.jpg",
                title: "",
                alignment: Alignment.center,
              ),
            ),
          ],
        ),
      ],
    );
  }
}

class _PromoTile extends StatelessWidget {
  final double height;
  final String image;
  final String title;
  final Alignment alignment;

  const _PromoTile({
    required this.height,
    required this.image,
    required this.title,
    required this.alignment,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      width: double.infinity,
      decoration: BoxDecoration(
        image: DecorationImage(image: AssetImage(image), fit: BoxFit.cover),
      ),
      child: Container(
        padding: const EdgeInsets.all(16),
        alignment: alignment,
        child: Text(
          title,
          style: const TextStyle(
            color: Colors.white,
            fontSize: 28,
            height: 1,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}

class _PromoTextBox extends StatelessWidget {
  final double height;
  final String title;

  const _PromoTextBox({required this.height, required this.title});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      color: Colors.white,
      padding: const EdgeInsets.all(18),
      alignment: Alignment.centerLeft,
      child: Text(
        title,
        style: const TextStyle(
          color: Color(0xFFDB3022),
          fontSize: 26,
          height: 1,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}
