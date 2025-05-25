package com.example.d4cassignment.data

data class CartItem(
    val product: ProductData,
    val quantity: Int
)

fun getCartItems() = listOf(
    CartItem(
        product = getProductItems()[0],
        quantity = 1
    ),
    CartItem(
        product = getProductItems()[1],
        quantity = 2
    )
) 