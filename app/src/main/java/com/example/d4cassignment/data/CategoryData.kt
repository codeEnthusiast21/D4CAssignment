package com.example.d4cassignment.data

data class CategoryData(
    val name: String
)
fun getCategoryItems() = listOf(
    CategoryData("Cleanser"),
    CategoryData("Toner"),
    CategoryData("Serums"),
    CategoryData("Moisturizer"),
    CategoryData("Sunscreen"),
    CategoryData("Face Mask"),
    CategoryData("Eye Cream"),
    CategoryData("Face Oil"),
    CategoryData("Exfoliator"),
    CategoryData("Face Mist"),
    CategoryData("Lip Care"),
    CategoryData("Face Wash")
)