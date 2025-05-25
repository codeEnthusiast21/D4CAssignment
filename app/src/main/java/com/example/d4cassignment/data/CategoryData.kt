package com.example.d4cassignment.data

data class CategoryData(
    val name: String
)
fun getCategoryItems() = listOf(
    CategoryData("Cleanser"),
    CategoryData("Toner"),
    CategoryData("Serums"),
    CategoryData("Moisturizers"),
    CategoryData("Sunscreen")
)