import 'package:flutter/material.dart';

import '../../services/favorite_service.dart';
import '../shop/product_detail_page.dart';

class FavoritesPage extends StatefulWidget {
  const FavoritesPage({super.key});

  @override
  State<FavoritesPage> createState() => _FavoritesPageState();
}

class _FavoritesPageState extends State<FavoritesPage> {
  final FavoriteService favoriteService = FavoriteService();
  bool isGridView = false;
  bool isLoading = true;
  List<dynamic> favorites = [];

  @override
  void initState() {
    super.initState();
    loadFavorites();
  }

  Future<void> loadFavorites() async {
    setState(() {
      isLoading = true;
    });

    final data = await favoriteService.getFavorites();

    if (!mounted) return;

    setState(() {
      favorites = data;
      isLoading = false;
    });
  }

  String getText(dynamic item, String key) {
    if (item == null) return "";
    if (item[key] == null) return "";
    return item[key].toString();
  }

  dynamic getProduct(dynamic item) {
    return item["product"] ?? {};
  }

  String getImage(dynamic product) {
    final image =
        product["imageUrl"] ?? product["image"] ?? product["thumbnail"] ?? "";

    return image.toString();
  }

  double getPrice(dynamic product) {
    final value =
        product["salePrice"] ??
        product["price"] ??
        product["comparePrice"] ??
        0;

    if (value is num) return value.toDouble();

    return double.tryParse(value.toString()) ?? 0;
  }

  String getProductName(dynamic product) {
    return (product["productName"] ??
            product["name"] ??
            product["title"] ??
            "Product")
        .toString();
  }

  String getBrand(dynamic product) {
    return (product["brand"] ?? product["brandName"] ?? "Brand").toString();
  }

  Future<void> confirmRemoveFavorite(dynamic item) async {
    final product = getProduct(item);

    final confirm = await showDialog<bool>(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text("Remove favorite?"),
        content: const Text(
          "Do you want to remove this product from favorites?",
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text("Cancel"),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, true),
            child: const Text(
              "Remove",
              style: TextStyle(color: Color(0xFFDB3022)),
            ),
          ),
        ],
      ),
    );

    if (confirm != true) return;

    final ok = await favoriteService.removeFromFavorite(
      productId: product["id"].toString(),
      sizeText: getText(item, "sizeText"),
      colorText: getText(item, "colorText"),
    );

    if (!mounted) return;

    if (ok) {
      await loadFavorites();

      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Removed from favorites")));
    } else {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Remove favorite failed")));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),
      appBar: AppBar(
        backgroundColor: const Color(0xFFF9F9F9),
        elevation: 0,
        automaticallyImplyLeading: false,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh, color: Colors.black),
            onPressed: loadFavorites,
          ),
          const Padding(
            padding: EdgeInsets.only(right: 16),
            child: Icon(Icons.search, color: Colors.black),
          ),
        ],
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Padding(
            padding: EdgeInsets.fromLTRB(16, 8, 16, 8),
            child: Text(
              "Favorites",
              style: TextStyle(
                fontSize: 34,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),

          SizedBox(
            height: 36,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 16),
              children: const [
                _ChipText(text: "Summer"),
                _ChipText(text: "T-Shirts"),
                _ChipText(text: "Shirts"),
                _ChipText(text: "New"),
              ],
            ),
          ),

          const SizedBox(height: 8),

          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Row(
              children: [
                const Icon(Icons.filter_list, size: 18),
                const SizedBox(width: 6),
                const Text("Filters", style: TextStyle(fontSize: 12)),
                const Spacer(),
                const Icon(Icons.swap_vert, size: 18),
                const SizedBox(width: 6),
                const Text(
                  "Price: lowest to high",
                  style: TextStyle(fontSize: 12),
                ),
                const Spacer(),

                GestureDetector(
                  onTap: () {
                    setState(() {
                      isGridView = !isGridView;
                    });
                  },
                  child: Icon(
                    isGridView ? Icons.view_list : Icons.grid_view,
                    size: 18,
                  ),
                ),
              ],
            ),
          ),

          const SizedBox(height: 12),

          Expanded(
            child: isLoading
                ? const Center(
                    child: CircularProgressIndicator(color: Color(0xFFDB3022)),
                  )
                : favorites.isEmpty
                ? const Center(
                    child: Text(
                      "No favorites yet",
                      style: TextStyle(color: Colors.grey),
                    ),
                  )
                : RefreshIndicator(
                    color: const Color(0xFFDB3022),
                    onRefresh: loadFavorites,
                    child: isGridView
                        ? GridView.builder(
                            padding: const EdgeInsets.symmetric(horizontal: 16),
                            itemCount: favorites.length,
                            gridDelegate:
                                const SliverGridDelegateWithFixedCrossAxisCount(
                                  crossAxisCount: 2,
                                  mainAxisSpacing: 18,
                                  crossAxisSpacing: 14,
                                  childAspectRatio: 0.58,
                                ),
                            itemBuilder: (context, index) {
                              final item = favorites[index];
                              final product = getProduct(item);

                              return GestureDetector(
                                onTap: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (_) => ProductDetailPage(
                                        productId: product["id"].toString(),
                                        initialFavorite: true,
                                      ),
                                    ),
                                  );
                                },
                                child: _FavoriteGridItem(
                                  image: getImage(product),
                                  brand: getBrand(product),
                                  name: getProductName(product),
                                  color: getText(item, "colorText"),
                                  size: getText(item, "sizeText"),
                                  price: getPrice(product),
                                  onRemove: () {
                                    confirmRemoveFavorite(item);
                                  },
                                ),
                              );
                            },
                          )
                        : ListView.builder(
                            padding: const EdgeInsets.symmetric(horizontal: 16),
                            itemCount: favorites.length,
                            itemBuilder: (context, index) {
                              final item = favorites[index];
                              final product = getProduct(item);

                              return GestureDetector(
                                onTap: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (_) => ProductDetailPage(
                                        productId: product["id"].toString(),
                                        initialFavorite: true,
                                      ),
                                    ),
                                  );
                                },
                                child: _FavoriteItem(
                                  image: getImage(product),
                                  brand: getBrand(product),
                                  name: getProductName(product),
                                  color: getText(item, "colorText"),
                                  size: getText(item, "sizeText"),
                                  price: getPrice(product),
                                  onRemove: () {
                                    confirmRemoveFavorite(item);
                                  },
                                ),
                              );
                            },
                          ),
                  ),
          ),
        ],
      ),
    );
  }
}

class _ChipText extends StatelessWidget {
  final String text;

  const _ChipText({required this.text});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 30,
      margin: const EdgeInsets.only(right: 8),
      padding: const EdgeInsets.symmetric(horizontal: 18),
      alignment: Alignment.center,
      decoration: BoxDecoration(
        color: Colors.black,
        borderRadius: BorderRadius.circular(18),
      ),
      child: Text(
        text,
        style: const TextStyle(color: Colors.white, fontSize: 12),
      ),
    );
  }
}

class _FavoriteItem extends StatelessWidget {
  final String image;
  final String brand;
  final String name;
  final String color;
  final String size;
  final double price;
  final VoidCallback onRemove;

  const _FavoriteItem({
    required this.image,
    required this.brand,
    required this.name,
    required this.color,
    required this.size,
    required this.price,
    required this.onRemove,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 116,
      margin: const EdgeInsets.only(bottom: 14),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
      ),
      child: Stack(
        children: [
          Row(
            children: [
              ClipRRect(
                borderRadius: const BorderRadius.horizontal(
                  left: Radius.circular(8),
                ),
                child: image.isNotEmpty
                    ? Image.asset(
                        "assets/images/$image",
                        width: 104,
                        height: 116,
                        fit: BoxFit.cover,
                        errorBuilder: (_, __, ___) {
                          return Container(
                            width: 104,
                            height: 116,
                            color: Colors.grey.shade300,
                            child: const Icon(Icons.image),
                          );
                        },
                      )
                    : Container(
                        width: 104,
                        height: 116,
                        color: Colors.grey.shade300,
                        child: const Icon(Icons.image),
                      ),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(12, 10, 34, 8),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        brand,
                        style: const TextStyle(
                          color: Colors.grey,
                          fontSize: 10,
                        ),
                      ),
                      Text(
                        name,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 15,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Row(
                        children: [
                          Text(
                            "Color: ",
                            style: TextStyle(
                              color: Colors.grey.shade600,
                              fontSize: 11,
                            ),
                          ),
                          Text(color, style: const TextStyle(fontSize: 11)),
                          const SizedBox(width: 14),
                          Text(
                            "Size: ",
                            style: TextStyle(
                              color: Colors.grey.shade600,
                              fontSize: 11,
                            ),
                          ),
                          Text(size, style: const TextStyle(fontSize: 11)),
                        ],
                      ),
                      const Spacer(),
                      Row(
                        children: [
                          Text(
                            "\$${price.toStringAsFixed(0)}",
                            style: const TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 14,
                            ),
                          ),
                          const SizedBox(width: 18),
                          ...List.generate(
                            5,
                            (index) => const Icon(
                              Icons.star,
                              size: 14,
                              color: Color(0xFFFFBA49),
                            ),
                          ),
                          const Text(
                            "(0)",
                            style: TextStyle(color: Colors.grey, fontSize: 10),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),

          Positioned(
            top: 8,
            right: 10,
            child: GestureDetector(
              onTap: onRemove,
              child: const Icon(Icons.close, size: 18, color: Colors.grey),
            ),
          ),

          Positioned(
            right: 8,
            bottom: 8,
            child: Container(
              width: 36,
              height: 36,
              decoration: const BoxDecoration(
                color: Color(0xFFDB3022),
                shape: BoxShape.circle,
              ),
              child: const Icon(
                Icons.shopping_bag_outlined,
                color: Colors.white,
                size: 19,
              ),
            ),
          ),
        ],
      ),
    );
  }
}


class _FavoriteGridItem extends StatelessWidget {
  final String image;
  final String brand;
  final String name;
  final String color;
  final String size;
  final double price;
  final VoidCallback onRemove;

  const _FavoriteGridItem({
    required this.image,
    required this.brand,
    required this.name,
    required this.color,
    required this.size,
    required this.price,
    required this.onRemove,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Stack(
              children: [
                ClipRRect(
                  borderRadius: BorderRadius.circular(4),
                  child: image.isNotEmpty
                      ? Image.asset(
                          "assets/images/$image",
                          width: double.infinity,
                          height: 190,
                          fit: BoxFit.cover,
                          errorBuilder: (_, __, ___) {
                            return Container(
                              height: 190,
                              color: Colors.grey.shade300,
                              child: const Icon(Icons.image),
                            );
                          },
                        )
                      : Container(
                          height: 190,
                          color: Colors.grey.shade300,
                          child: const Icon(Icons.image),
                        ),
                ),

                Positioned(
                  top: 6,
                  right: 6,
                  child: GestureDetector(
                    onTap: onRemove,
                    child: Container(
                      width: 24,
                      height: 24,
                      decoration: BoxDecoration(
                        color: Colors.grey.shade200,
                        shape: BoxShape.circle,
                      ),
                      child: const Icon(
                        Icons.close,
                        size: 16,
                        color: Colors.grey,
                      ),
                    ),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 4),

            Row(
              children: List.generate(
                5,
                (index) => const Icon(
                  Icons.star_border,
                  size: 13,
                  color: Colors.grey,
                ),
              ),
            ),

            Text(
              brand,
              style: const TextStyle(color: Colors.grey, fontSize: 11),
            ),

            Text(
              name,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
              style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 14),
            ),

            Text(
              "Color: $color   Size: $size",
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
              style: const TextStyle(color: Colors.grey, fontSize: 11),
            ),

            Text(
              "\$${price.toStringAsFixed(0)}",
              style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 13),
            ),
          ],
        ),

        Positioned(
          right: 0,
          bottom: 12,
          child: Container(
            width: 36,
            height: 36,
            decoration: const BoxDecoration(
              color: Color(0xFFDB3022),
              shape: BoxShape.circle,
            ),
            child: const Icon(
              Icons.shopping_bag_outlined,
              color: Colors.white,
              size: 18,
            ),
          ),
        ),
      ],
    );
  }
}






