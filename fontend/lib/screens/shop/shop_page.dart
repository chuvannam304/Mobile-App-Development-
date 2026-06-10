import 'package:flutter/material.dart';

import '../../models/shop_category.dart';
import '../../services/shop_service.dart';
import 'category_detail_page.dart';

class ShopPage extends StatefulWidget {
  const ShopPage({super.key});

  @override
  State<ShopPage> createState() => _ShopPageState();
}

class _ShopPageState extends State<ShopPage> with TickerProviderStateMixin {
  final ShopService shopService = ShopService();

  List<ShopCategory> parentCategories = [];
  List<ShopCategory> childCategories = [];

  TabController? tabController;

  bool isLoadingParents = true;
  bool isLoadingChildren = false;

  int parentOrder(String name) {
    const order = ["Women", "Men", "Kids"];
    return order.indexOf(name);
  }

  int childOrder(String name) {
    if (name.contains("New")) return 0;
    if (name.contains("Clothes")) return 1;
    if (name.contains("Shoes")) return 2;
    if (name.contains("Accessories")) return 3;
    return 99;
  }

  @override
  void initState() {
    super.initState();
    loadParents();
  }

  Future<void> loadParents() async {
    final parents = await shopService.getParentCategories();
    parents.sort(
      (a, b) =>
          parentOrder(a.categoryName).compareTo(parentOrder(b.categoryName)),
    );
    if (!mounted) return;

    parentCategories = parents;

    tabController = TabController(length: parentCategories.length, vsync: this);

    tabController!.addListener(() {
      if (!tabController!.indexIsChanging) {
        loadChildren(parentCategories[tabController!.index].id);
      }
    });

    setState(() {
      isLoadingParents = false;
    });

    if (parentCategories.isNotEmpty) {
      loadChildren(parentCategories[0].id);
    }
  }

  Future<void> loadChildren(String parentId) async {
    setState(() {
      isLoadingChildren = true;
    });

    final children = await shopService.getChildrenCategories(parentId);

    children.sort(
      (a, b) =>
          childOrder(a.categoryName).compareTo(childOrder(b.categoryName)),
    );

    if (!mounted) return;

    setState(() {
      childCategories = children;
      isLoadingChildren = false;
    });
  }

  String cleanCategoryName(String name) {
    return name
        .replaceFirst("Women ", "")
        .replaceFirst("Men ", "")
        .replaceFirst("Kids ", "");
  }

  @override
  void dispose() {
    tabController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (isLoadingParents) {
      return const Scaffold(
        backgroundColor: Color(0xFFF9F9F9),
        body: Center(
          child: CircularProgressIndicator(color: Color(0xFFDB3022)),
        ),
      );
    }

    if (parentCategories.isEmpty || tabController == null) {
      return const Scaffold(
        backgroundColor: Color(0xFFF9F9F9),
        body: Center(child: Text("No categories")),
      );
    }

    return Scaffold(
      backgroundColor: const Color(0xFFF9F9F9),

      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        centerTitle: true,

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

        bottom: TabBar(
          controller: tabController,
          indicatorColor: const Color(0xFFDB3022),
          labelColor: Colors.black,
          unselectedLabelColor: Colors.grey,
          tabs: parentCategories
              .map((category) => Tab(text: category.categoryName))
              .toList(),
        ),
      ),

      body: Column(
        children: [
          Container(
            margin: const EdgeInsets.all(16),
            width: double.infinity,
            height: 100,
            decoration: BoxDecoration(
              color: const Color(0xFFDB3022),
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  "SUMMER SALES",
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  "Up to 50% off",
                  style: TextStyle(color: Colors.white, fontSize: 14),
                ),
              ],
            ),
          ),

          Expanded(
            child: isLoadingChildren
                ? const Center(
                    child: CircularProgressIndicator(color: Color(0xFFDB3022)),
                  )
                : ListView.builder(
                    itemCount: childCategories.length,
                    itemBuilder: (context, index) {
                      final category = childCategories[index];

                      return GestureDetector(
  onTap: () {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (_) => CategoryDetailPage(
          parentCategory: category,
        ),
      ),
    );
  },
  child: Container(
    margin: const EdgeInsets.symmetric(
      horizontal: 16,
      vertical: 8,
    ),
    height: 100,
    decoration: BoxDecoration(
      color: Colors.white,
      borderRadius: BorderRadius.circular(12),
      boxShadow: [
        BoxShadow(
          color: Colors.black.withOpacity(0.05),
          blurRadius: 8,
        ),
      ],
    ),
    child: Row(
      children: [
        Expanded(
          child: Padding(
            padding: const EdgeInsets.all(16),
            child: Text(
              cleanCategoryName(category.categoryName),
              style: const TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
        ClipRRect(
          borderRadius: const BorderRadius.only(
            topRight: Radius.circular(12),
            bottomRight: Radius.circular(12),
          ),
          child: SizedBox(
            width: 120,
            height: 100,
            child: category.image.isNotEmpty
                ? Image.asset(
                    "assets/images/${category.image}",
                    fit: BoxFit.cover,
                  )
                : Container(
                    color: Colors.grey.shade300,
                    child: const Icon(Icons.image),
                  ),
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
      ),
    );
  }
}
