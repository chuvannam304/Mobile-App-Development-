import 'package:flutter/material.dart';

import '../../models/category_product.dart';
import '../../models/shop_category.dart';
import '../../services/category_product_service.dart';
import 'product_detail_page.dart';

class CategoryProductsPage extends StatefulWidget {
  final ShopCategory category;

  final bool viewAll;

  const CategoryProductsPage({
    super.key,
    required this.category,
    this.viewAll = false,
  });
  @override
  State<CategoryProductsPage> createState() => _CategoryProductsPageState();
}

class _CategoryProductsPageState extends State<CategoryProductsPage> {
  final CategoryProductService service = CategoryProductService();

  List<CategoryProduct> products = [];

  bool isLoading = true;
  bool isGrid = false;
  String selectedSort = "Price: lowest to high";
  @override
  void initState() {
    super.initState();
    loadProducts();
  }

  Future<void> loadProducts() async {
    final data = widget.viewAll
        ? await service.getAllProductsByCategory(widget.category.id)
        : await service.getProductsByCategory(widget.category.id);

    if (!mounted) return;

    setState(() {
      products = data;
      isLoading = false;
    });
  }

  String cleanTitle(String name) {
    return name
        .replaceFirst("Women ", "")
        .replaceFirst("Men ", "")
        .replaceFirst("Kids ", "")
        .replaceFirst("New ", "")
        .replaceFirst("Clothes ", "")
        .replaceFirst("Shoes ", "")
        .replaceFirst("Accessories ", "");
  }

  @override
  Widget build(BuildContext context) {
    String getGenderTitle(String name) {
      if (name.contains("Men")) return "Men’s";
      if (name.contains("Kids")) return "Kids";
      return "Women’s";
    }

    final title =
        "${getGenderTitle(widget.category.categoryName)} "
        "${cleanTitle(widget.category.categoryName).toLowerCase()}";

    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
        actions: const [
          Padding(
            padding: EdgeInsets.only(right: 16),
            child: Icon(Icons.search, color: Colors.black),
          ),
        ],
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Text(
              title,
              style: const TextStyle(
                fontSize: 34,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
          ),

          const SizedBox(height: 12),

          SizedBox(
            height: 36,
            child: ListView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 16),
              children: const [
                _ChipItem(text: "T-shirts"),
                _ChipItem(text: "Crop tops"),
                _ChipItem(text: "Blouses"),
                _ChipItem(text: "Sleeveless"),
              ],
            ),
          ),

          const SizedBox(height: 12),

          GestureDetector(
            onTap: () {
              showModalBottomSheet(
                context: context,
                shape: const RoundedRectangleBorder(
                  borderRadius: BorderRadius.vertical(top: Radius.circular(24)),
                ),
                builder: (_) {
                  return Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      const SizedBox(height: 20),

                      const Text(
                        "Sort by",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      const SizedBox(height: 20),

                      ListTile(
                        title: const Text("Popular"),
                        onTap: () => Navigator.pop(context),
                      ),

                      ListTile(
                        title: const Text("Newest"),
                        onTap: () => Navigator.pop(context),
                      ),

                      ListTile(
                        title: const Text("Customer review"),
                        onTap: () => Navigator.pop(context),
                      ),

                      Container(
                        color: const Color(0xFFDB3022),
                        child: ListTile(
                          title: const Text(
                            "Price: lowest to high",
                            style: TextStyle(color: Colors.white),
                          ),
                          onTap: () {
                            setState(() {
                              selectedSort = "Price: lowest to high";

                              products.sort(
                                (a, b) => a.salePrice.compareTo(b.salePrice),
                              );
                            });

                            Navigator.pop(context);
                          },
                        ),
                      ),

                      ListTile(
                        title: const Text("Price: highest to low"),
                        onTap: () {
                          setState(() {
                            selectedSort = "Price: highest to low";

                            products.sort(
                              (a, b) => b.salePrice.compareTo(a.salePrice),
                            );
                          });

                          Navigator.pop(context);
                        },
                      ),
                    ],
                  );
                },
              );
            },
            child: Row(
              children: [
                const Icon(Icons.swap_vert, size: 20),
                const SizedBox(width: 6),
                Text(selectedSort, style: const TextStyle(fontSize: 12)),
              ],
            ),
          ),
          Expanded(
            child: isLoading
                ? const Center(
                    child: CircularProgressIndicator(color: Color(0xFFDB3022)),
                  )
                : products.isEmpty
                ? const Center(child: Text("No products"))
                : isGrid
                ? _buildGridProducts()
                : _buildListProducts(),
          ),
        ],
      ),
    );
  }

  Widget _buildListProducts() {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: products.length,
      itemBuilder: (context, index) {
        final product = products[index];

        return GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (_) => ProductDetailPage(productId: product.id),
              ),
            );
          },
          child: _ProductListCard(product: product, isFavorite: index == 2),
        );
      },
    );
  }

  Widget _buildGridProducts() {
    return GridView.builder(
      padding: const EdgeInsets.all(16),
      itemCount: products.length,
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        childAspectRatio: 0.58,
        crossAxisSpacing: 14,
        mainAxisSpacing: 18,
      ),
      itemBuilder: (context, index) {
        final product = products[index];

        return GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (_) => ProductDetailPage(productId: product.id),
              ),
            );
          },
          child: _ProductGridCard(product: product, isFavorite: index == 2),
        );
      },
    );
  }
}

class _ChipItem extends StatelessWidget {
  final String text;

  const _ChipItem({required this.text});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(right: 8),
      padding: const EdgeInsets.symmetric(horizontal: 18),
      decoration: BoxDecoration(
        color: const Color(0xFF222222),
        borderRadius: BorderRadius.circular(18),
      ),
      child: Center(
        child: Text(
          text,
          style: const TextStyle(color: Colors.white, fontSize: 13),
        ),
      ),
    );
  }
}

class _ProductListCard extends StatelessWidget {
  final CategoryProduct product;
  final bool isFavorite;

  const _ProductListCard({required this.product, required this.isFavorite});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 132,
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.06),
            blurRadius: 10,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: Row(
        children: [
          ClipRRect(
            borderRadius: const BorderRadius.only(
              topLeft: Radius.circular(10),
              bottomLeft: Radius.circular(10),
            ),
            child: SizedBox(
              width: 128,
              height: 132,
              child: product.imageUrl.isNotEmpty
                  ? Image.asset(
                      "assets/images/${product.imageUrl}",
                      fit: BoxFit.cover,
                    )
                  : Container(
                      color: Colors.grey.shade300,
                      child: const Icon(Icons.image),
                    ),
            ),
          ),

          Expanded(
            child: Padding(
              padding: const EdgeInsets.fromLTRB(12, 10, 8, 8),
              child: Stack(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(right: 34),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          product.productName,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(
                            fontSize: 17,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          ),
                        ),
                        const SizedBox(height: 3),
                        Text(
                          product.shortDescription,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(
                            fontSize: 12,
                            color: Colors.grey,
                          ),
                        ),
                        const SizedBox(height: 8),
                        Row(
                          children: [
                            ...List.generate(
                              5,
                              (index) => Icon(
                                index < 4 ? Icons.star : Icons.star_border,
                                color: const Color(0xFFFFBA49),
                                size: 15,
                              ),
                            ),
                            const SizedBox(width: 4),
                            const Text(
                              "(3)",
                              style: TextStyle(
                                fontSize: 11,
                                color: Colors.grey,
                              ),
                            ),
                          ],
                        ),
                        const Spacer(),
                        Text(
                          "\$${product.salePrice.toStringAsFixed(0)}",
                          style: const TextStyle(
                            fontSize: 15,
                            color: Colors.black,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ],
                    ),
                  ),

                  Positioned(
                    right: 0,
                    bottom: 0,
                    child: _FavoriteButton(isFavorite: isFavorite),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class _ProductGridCard extends StatelessWidget {
  final CategoryProduct product;
  final bool isFavorite;

  const _ProductGridCard({required this.product, required this.isFavorite});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Expanded(
          child: Stack(
            children: [
              ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: SizedBox(
                  width: double.infinity,
                  height: double.infinity,
                  child: product.imageUrl.isNotEmpty
                      ? Image.asset(
                          "assets/images/${product.imageUrl}",
                          fit: BoxFit.cover,
                        )
                      : Container(
                          color: Colors.grey.shade300,
                          child: const Icon(Icons.image),
                        ),
                ),
              ),

              if (product.comparePrice > product.salePrice)
                Positioned(
                  top: 8,
                  left: 8,
                  child: Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 8,
                      vertical: 4,
                    ),
                    decoration: BoxDecoration(
                      color: const Color(0xFFDB3022),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: const Text(
                      "-20%",
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),

              Positioned(
                right: 0,
                bottom: 0,
                child: _FavoriteButton(isFavorite: isFavorite),
              ),
            ],
          ),
        ),

        const SizedBox(height: 6),

        Text(
          product.shortDescription,
          maxLines: 1,
          overflow: TextOverflow.ellipsis,
          style: const TextStyle(fontSize: 11, color: Colors.grey),
        ),

        Text(
          product.productName,
          maxLines: 1,
          overflow: TextOverflow.ellipsis,
          style: const TextStyle(
            fontSize: 15,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),

        const SizedBox(height: 4),

        Row(
          children: [
            ...List.generate(
              5,
              (index) => Icon(
                index < 4 ? Icons.star : Icons.star_border,
                color: const Color(0xFFFFBA49),
                size: 14,
              ),
            ),
            const SizedBox(width: 3),
            const Text(
              "(3)",
              style: TextStyle(fontSize: 10, color: Colors.grey),
            ),
          ],
        ),

        const SizedBox(height: 4),

        Text(
          "\$${product.salePrice.toStringAsFixed(0)}",
          style: const TextStyle(
            fontSize: 14,
            color: Colors.black,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
}

class _FavoriteButton extends StatelessWidget {
  final bool isFavorite;

  const _FavoriteButton({required this.isFavorite});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 38,
      height: 38,
      decoration: BoxDecoration(
        color: Colors.white,
        shape: BoxShape.circle,
        boxShadow: [
          BoxShadow(color: Colors.black.withOpacity(0.12), blurRadius: 8),
        ],
      ),
      child: Icon(
        isFavorite ? Icons.favorite : Icons.favorite_border,
        color: isFavorite ? const Color(0xFFDB3022) : Colors.grey,
        size: 22,
      ),
    );
  }
}
