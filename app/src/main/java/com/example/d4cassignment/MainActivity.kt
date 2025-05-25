package com.example.d4cassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.d4cassignment.data.CartItem
import com.example.d4cassignment.data.ProductData
import com.example.d4cassignment.ui.theme.D4CAssignmentTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            D4CAssignmentTheme {
                ShopScreen()
            }
        }
    }
}

@Composable
fun ShopScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Shop) }
    val cartItems = remember { mutableStateMapOf<String, CartItem>() }
    val wishlistItems = remember { mutableStateMapOf<String, ProductData>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Force recomposition when cart or wishlist changes
    LaunchedEffect(cartItems.size, wishlistItems.size) {
        // This will trigger recomposition when the maps change
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (currentScreen) {
            Screen.Shop -> ShopContent(
                modifier = Modifier.padding(paddingValues),
                onNavigateToCart = { currentScreen = Screen.Cart },
                onNavigateToWishlist = { currentScreen = Screen.Wishlist },
                cartItems = cartItems,
                wishlistItems = wishlistItems,
                onAddToCart = { product ->
                    val key = product.brand
                    cartItems[key] = cartItems[key]?.copy(quantity = cartItems[key]!!.quantity + 1)
                        ?: CartItem(product, 1)
                    scope.launch {
                        snackbarHostState.showSnackbar("Added to cart: ${product.brand}")
                    }
                },
                onToggleWishlist = { product ->
                    val key = product.brand
                    if (wishlistItems.containsKey(key)) {
                        wishlistItems.remove(key)
                        scope.launch {
                            snackbarHostState.showSnackbar("Removed from wishlist: ${product.brand}")
                        }
                    } else {
                        wishlistItems[key] = product
                        scope.launch {
                            snackbarHostState.showSnackbar("Added to wishlist: ${product.brand}")
                        }
                    }
                }
            )
            Screen.Cart -> CartScreen(
                modifier = Modifier.padding(paddingValues),
                cartItems = cartItems.values.toList(),
                onBack = { currentScreen = Screen.Shop },
                onUpdateQuantity = { item, newQuantity ->
                    if (newQuantity <= 0) {
                        cartItems.remove(item.product.brand)
                        scope.launch {
                            snackbarHostState.showSnackbar("Removed from cart: ${item.product.brand}")
                        }
                    } else {
                        cartItems[item.product.brand] = item.copy(quantity = newQuantity)
                        scope.launch {
                            snackbarHostState.showSnackbar("Updated quantity: ${item.product.brand}")
                        }
                    }
                },
                onMoveToWishlist = { item ->
                    wishlistItems[item.product.brand] = item.product
                    cartItems.remove(item.product.brand)
                    scope.launch {
                        snackbarHostState.showSnackbar("Moved to wishlist: ${item.product.brand}")
                    }
                }
            )
            Screen.Wishlist -> WishlistScreen(
                modifier = Modifier.padding(paddingValues),
                wishlistItems = wishlistItems.values.toList(),
                onBack = { currentScreen = Screen.Shop },
                onAddToCart = { product ->
                    val key = product.brand
                    cartItems[key] = cartItems[key]?.copy(quantity = cartItems[key]!!.quantity + 1)
                        ?: CartItem(product, 1)
                    scope.launch {
                        snackbarHostState.showSnackbar("Added to cart: ${product.brand}")
                    }
                },
                onRemoveFromWishlist = { product ->
                    wishlistItems.remove(product.brand)
                    scope.launch {
                        snackbarHostState.showSnackbar("Removed from wishlist: ${product.brand}")
                    }
                }
            )
        }
    }
}

@Composable
fun ShopContent(
    modifier: Modifier = Modifier,
    onNavigateToCart: () -> Unit,
    onNavigateToWishlist: () -> Unit,
    cartItems: Map<String, CartItem>,
    wishlistItems: Map<String, ProductData>,
    onAddToCart: (ProductData) -> Unit,
    onToggleWishlist: (ProductData) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
            .verticalScroll(scrollState)
    ) {
        TopAppBar(
            cartCount = cartItems.values.sumOf { it.quantity },
            wishlistCount = wishlistItems.size,
            onCartClick = onNavigateToCart,
            onWishlistClick = onNavigateToWishlist
        )

        PromotionalBanner()
        CategoriesSection()
        NewProductsSection(
            cartItems = cartItems,
            wishlistItems = wishlistItems,
            onAddToCart = onAddToCart,
            onToggleWishlist = onToggleWishlist
        )
    }
}


