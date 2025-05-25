package com.example.d4cassignment.data

data class ProductData(
    val brand: String,
    val description: String,
    val category: String,
    val currentPrice: String,
    val originalPrice: String,
    val reviewCount: Int,
    val inStock: Boolean,
    val isBestSeller: Boolean,
    val isFavorite: Boolean
)

fun getProductItems() = listOf(
    ProductData(
        brand = "clencera",
        description = "French clay and algae-powered cleanser",
        category = "Skin Tightness • Dry & Dehydrated Skin",
        currentPrice = "355.20",
        originalPrice = "444.00",
        reviewCount = 249,
        inStock = true,
        isBestSeller = true,
        isFavorite = true
    ),
    ProductData(
        brand = "glow",
        description = "French clay and algae-powered cleanser",
        category = "Skin Tightness • Dry & Dehydrated Skin",
        currentPrice = "355.20",
        originalPrice = "444.00",
        reviewCount = 249,
        inStock = true,
        isBestSeller = false,
        isFavorite = false
    ),
    ProductData(
        brand = "afterglow",
        description = "French clay and algae-powered cleanser",
        category = "Skin Tightness • Dry & Dehydrated Skin",
        currentPrice = "355.20",
        originalPrice = "444.00",
        reviewCount = 249,
        inStock = false,
        isBestSeller = false,
        isFavorite = false
    )
)