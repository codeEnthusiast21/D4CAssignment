package com.example.d4cassignment.data

data class BannerData(
    val title: String,
    val subtitle: String,
    val dateRange: String
)

fun getBannerItems() = listOf(
    BannerData(
        title = "GET 20% OFF",
        subtitle = "Get 20% off",
        dateRange = "12-18 October"
    ),
    BannerData(
        title = "FREE SHIPPING",
        subtitle = "On orders above ₹499",
        dateRange = "Valid till 31st"
    ),
    BannerData(
        title = "NEW ARRIVALS",
        subtitle = "Fresh skincare collection",
        dateRange = "Just launched"
    )
)
