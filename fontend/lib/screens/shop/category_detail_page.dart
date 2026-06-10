import 'package:flutter/material.dart';

import 'category_products_page.dart';
import '../../models/shop_category.dart';
import '../../services/shop_service.dart';

class CategoryDetailPage extends StatefulWidget {
  final ShopCategory parentCategory;

  const CategoryDetailPage({super.key, required this.parentCategory});

  @override
  State<CategoryDetailPage> createState() => _CategoryDetailPageState();
}

class _CategoryDetailPageState extends State<CategoryDetailPage> {
  final ShopService shopService = ShopService();

  late Future<List<ShopCategory>> childrenFuture;

  @override
  void initState() {
    super.initState();
    childrenFuture = shopService.getChildrenCategories(
      widget.parentCategory.id,
    );
  }

  String cleanName(String name) {
    return name
        .replaceFirst("Women ", "")
        .replaceFirst("Men ", "")
        .replaceFirst("Kids ", "")
        .replaceFirst("New ", "")
        .replaceFirst("Clothes ", "")
        .replaceFirst("Shoes ", "")
        .replaceFirst("Accessories ", "");
  }

  int subCategoryOrder(String name) {
    if (name.contains("Tops")) return 0;
    if (name.contains("Shirts & Blouses")) return 1;
    if (name.contains("Cardigans & Sweaters")) return 2;
    if (name.contains("Knitwear")) return 3;
    if (name.contains("Blazers")) return 4;
    if (name.contains("Outerwear")) return 5;
    if (name.contains("Pants")) return 6;
    if (name.contains("Jeans")) return 7;
    if (name.contains("Shorts")) return 8;
    if (name.contains("Skirts")) return 9;
    if (name.contains("Dresses")) return 10;
    return 99;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),

      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        centerTitle: true,

        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios_new, color: Colors.black),
          onPressed: () {
            Navigator.pop(context);
          },
        ),

        title: const Text(
          "Categories",
          style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
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
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => CategoryProductsPage(
                    category: widget.parentCategory,
                    viewAll: true,
                  ),
                ),
              );
            },
            child: Container(
              margin: const EdgeInsets.all(16),
              width: double.infinity,
              height: 48,
              decoration: BoxDecoration(
                color: const Color(0xFFDB3022),
                borderRadius: BorderRadius.circular(24),
              ),
              child: const Center(
                child: Text(
                  "VIEW ALL ITEMS",
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 14,
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
            ),
          ),

          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 16),
            child: Text(
              "Choose category",
              style: TextStyle(fontSize: 14, color: Colors.grey),
            ),
          ),

          const SizedBox(height: 8),

          Expanded(
            child: FutureBuilder<List<ShopCategory>>(
              future: childrenFuture,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(
                    child: CircularProgressIndicator(color: Color(0xFFDB3022)),
                  );
                }

                final categories = snapshot.data ?? [];

                categories.sort(
                  (a, b) => subCategoryOrder(
                    a.categoryName,
                  ).compareTo(subCategoryOrder(b.categoryName)),
                );

                if (categories.isEmpty) {
                  return const Center(child: Text("No sub categories"));
                }

                return ListView.separated(
                  itemCount: categories.length,
                  separatorBuilder: (_, __) =>
                      const Divider(height: 1, color: Color(0xFFE0E0E0)),
                  itemBuilder: (context, index) {
                    final category = categories[index];

                    return ListTile(
                      contentPadding: const EdgeInsets.symmetric(
                        horizontal: 32,
                      ),
                      title: Text(
                        cleanName(category.categoryName),
                        style: const TextStyle(
                          fontSize: 16,
                          color: Colors.black87,
                        ),
                      ),
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) =>
                                CategoryProductsPage(category: category),
                          ),
                        );
                      },
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
