package com.example.d4cassignment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.d4cassignment.data.BannerData
import com.example.d4cassignment.data.CartItem
import com.example.d4cassignment.data.CategoryData
import com.example.d4cassignment.data.ProductData
import com.example.d4cassignment.data.getBannerItems
import com.example.d4cassignment.data.getCartItems
import com.example.d4cassignment.data.getCategoryItems
import com.example.d4cassignment.data.getProductItems
import kotlinx.coroutines.delay

// fonts
private val centuryOldStyle = FontFamily(
    Font(R.font.century_old_style_std_bold)
)

private val tangerine = FontFamily(
    Font(R.font.tangerine)
)

private val neuzeitSlt = FontFamily(
    Font(R.font.neuzeitsltstd_book)
)

@Composable
fun TopAppBar(
    cartCount: Int,
    wishlistCount: Int,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Shop",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = centuryOldStyle
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Wishlist Icon with Badge
            Box(
                modifier = Modifier.clickable { onWishlistClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                if (wishlistCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF9CFF2E), CircleShape)
                            .offset(x = 8.dp, y = (-8).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = wishlistCount.toString(),
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Cart Icon with Badge
            Box(
                modifier = Modifier.clickable { onCartClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                if (cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF9CFF2E), CircleShape)
                            .offset(x = 8.dp, y = (-8).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartCount.toString(),
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PromotionalBanner() {
    val bannerItems = getBannerItems()
    val pagerState = rememberPagerState(pageCount = { bannerItems.size })

    // Auto scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % bannerItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(140.dp)
        ) { page ->
            BannerItem(bannerItems[page])
        }

        // Page indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(bannerItems.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                        .background(
                            if (index == pagerState.currentPage) Color(0xFF9CFF2E) else Color.Gray,
                            CircleShape
                        )
                )
                if (index < bannerItems.size - 1) {
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}

@Composable
fun BannerItem(banner: BannerData) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background PNG
            Image(
                painter = painterResource(id = R.drawable.banner_card),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Content
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = banner.title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = banner.subtitle,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF9CFF2E), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = banner.dateRange,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Profile icon in top right
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = centuryOldStyle
            )
            Text(
                text = "See all",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = getCategoryItems()
            items(categories.size) { index ->
                CategoryItem(categories[index])
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = when (category.name.lowercase()) {
                    "sunscreen" -> R.drawable.categorysample
                    "serums" -> R.drawable.product_image
                    "toner" -> R.drawable.categorysample
                    "cleanser" -> R.drawable.product_image
                    "face mask" -> R.drawable.categorysample
                    "eye cream" -> R.drawable.product_image
                    "face oil" -> R.drawable.categorysample
                    "exfoliator" -> R.drawable.product_image
                    "face mist" -> R.drawable.categorysample
                    "lip care" -> R.drawable.product_image
                    "face wash" -> R.drawable.categorysample
                    else -> R.drawable.product_image
                }),
                contentDescription = "${category.name} category icon",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = neuzeitSlt
        )
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        TopAppBar(
            cartCount = cartItems.values.sumOf { it.quantity },
            wishlistCount = wishlistItems.size,
            onCartClick = onNavigateToCart,
            onWishlistClick = onNavigateToWishlist,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
}

@Composable
fun NewProductsSection(
    cartItems: Map<String, CartItem>,
    wishlistItems: Map<String, ProductData>,
    onAddToCart: (ProductData) -> Unit,
    onToggleWishlist: (ProductData) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New products",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = centuryOldStyle
            )
            Text(
                text = "See all",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val products = getProductItems()
            products.forEach { product ->
                ProductCard(
                    product = product,
                    isInWishlist = wishlistItems.containsKey(product.brand),
                    onAddToCart = { onAddToCart(product) },
                    onToggleWishlist = { onToggleWishlist(product) }
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: ProductData,
    isInWishlist: Boolean,
    onAddToCart: () -> Unit,
    onToggleWishlist: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(580.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_item_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Wishlist icon
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(32.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    .clickable { onToggleWishlist() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isInWishlist) "Remove from wishlist" else "Add to wishlist",
                    tint = if (isInWishlist) Color.Red else Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .align(Alignment.TopCenter)
            ) {

                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Best seller badge
                if (product.isBestSeller) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(Color.Black, RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Best seller",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Black Shape Card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(160.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                // Black Shape Background
                Image(
                    painter = painterResource(id = R.drawable.card_black_shape),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.brand,
                            color = Color(0xFF9CFF2E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = tangerine
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (product.inStock) Color(0xFF9CFF2E) else Color.Red,
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (product.inStock) "In stock" else "Out of stock",
                            color = if (product.inStock) Color(0xFF9CFF2E) else Color.Red,
                            fontSize = 10.sp,
                            fontFamily = neuzeitSlt
                        )
                    }

                    Text(
                        text = product.description,
                        color = Color.White,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = neuzeitSlt
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "RS. ${product.currentPrice}",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = neuzeitSlt
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "RS. ${product.originalPrice}",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                textDecoration = TextDecoration.LineThrough,
                                fontFamily = neuzeitSlt
                            )
                        }

                        // Add to cart button
                        Surface(
                            modifier = Modifier
                                .offset(x = 14.dp, y = 32.dp)
                                .size(52.dp)
                                .aspectRatio(1f)
                                .clickable { onAddToCart() },
                            shape = CircleShape,
                            color = Color.Transparent,
                            border = BorderStroke(1.dp, Color(0xFF9CFF2E))
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.cart),
                                    contentDescription = "Add to cart",
                                    modifier = Modifier.size(24.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onBack: () -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onMoveToWishlist: (CartItem) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Cart (${cartItems.sumOf { it.quantity }})",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = neuzeitSlt
            )
        }

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your cart is empty",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems.size) { index ->
                    CartItemCard(
                        item = cartItems[index],
                        onUpdateQuantity = { newQuantity -> onUpdateQuantity(cartItems[index], newQuantity) },
                        onMoveToWishlist = { onMoveToWishlist(cartItems[index]) }
                    )
                }
            }

            // Checkout Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    val total = cartItems.sumOf { it.product.currentPrice.toDouble() * it.quantity }
                    Text(
                        text = "Total: RS. ${String.format("%.2f", total)}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { /* nothing implemented yet */},
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CFF2E))
                    ) {
                        Text(
                            text = "Proceed to Checkout",
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onMoveToWishlist: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF2A2A2A), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.product.brand.take(2).uppercase(),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.product.brand,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.product.description,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "RS. ${item.product.currentPrice}",
                    color = Color(0xFF9CFF2E),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Quantity
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onUpdateQuantity(item.quantity - 1) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Decrease",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = item.quantity.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = { onUpdateQuantity(item.quantity + 1) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Move to Wishlist
                TextButton(
                    onClick = onMoveToWishlist
                ) {
                    Text(
                        text = "Move to Wishlist",
                        color = Color(0xFF9CFF2E),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    wishlistItems: List<ProductData>,
    onBack: () -> Unit,
    onAddToCart: (ProductData) -> Unit,
    onRemoveFromWishlist: (ProductData) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Wishlist (${wishlistItems.size})",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = neuzeitSlt
            )
        }

        if (wishlistItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your wishlist is empty",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(wishlistItems.size) { index ->
                    WishlistItemCard(
                        product = wishlistItems[index],
                        onAddToCart = { onAddToCart(wishlistItems[index]) },
                        onRemove = { onRemoveFromWishlist(wishlistItems[index]) }
                    )
                }
            }
        }
    }
}

@Composable
fun WishlistItemCard(
    product: ProductData,
    onAddToCart: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF2A2A2A), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.brand.take(2).uppercase(),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.brand,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = product.description,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RS. ${product.currentPrice}",
                        color = Color(0xFF9CFF2E),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RS. ${product.originalPrice}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.reviewCount} reviews",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = onAddToCart,
                    modifier = Modifier.padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9CFF2E))
                ) {
                    Text(
                        text = "Add to Cart",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                TextButton(
                    onClick = onRemove
                ) {
                    Text(
                        text = "Remove",
                        color = Color(0xFF9CFF2E),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}