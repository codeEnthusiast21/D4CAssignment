package com.example.d4cassignment.data

import com.example.d4cassignment.R

data class ProductData(
    val brand: String,
    val description: String,
    val category: String,
    val currentPrice: String,
    val originalPrice: String,
    val reviewCount: Int,
    val inStock: Boolean,
    val isBestSeller: Boolean,
    val isFavorite: Boolean,
    val imageResId: Int
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
        isFavorite = true,
        imageResId = R.drawable.product_image
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
        isFavorite = false,
        imageResId = R.drawable.categorysample
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
        isFavorite = false,
        imageResId = R.drawable.product_image
    )
)