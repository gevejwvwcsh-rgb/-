package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FloatingContactBar
import com.example.ui.components.StoreTopBar
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.DeveloperDashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProductsScreen
import com.example.ui.screens.ShowroomScreen
import com.example.ui.theme.AquaContainerLight
import com.example.ui.theme.AquaDeep
import com.example.ui.theme.AquaPrimary
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PureWhite
import com.example.ui.viewmodel.NavigationTab
import com.example.ui.viewmodel.StoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(
    viewModel: StoreViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (!uiState.userSession.isLoggedIn) {
        // Enforce phone auth with verification code or developer login
        AuthScreen(viewModel = viewModel)
    } else if (uiState.isDeveloperDashboardOpen) {
        // Developer dashboard with BackHandler
        BackHandler {
            viewModel.toggleDeveloperDashboard(false)
        }
        DeveloperDashboardScreen(
            viewModel = viewModel,
            onClose = { viewModel.toggleDeveloperDashboard(false) }
        )
    } else if (uiState.selectedProduct != null) {
        ProductDetailScreen(
            product = uiState.selectedProduct!!,
            viewModel = viewModel,
            onBack = { viewModel.selectProduct(null) }
        )
    } else {
        Scaffold(
            topBar = {
                StoreTopBar(
                    viewModel = viewModel,
                    cartCount = uiState.cartCount,
                    onCartClick = { viewModel.selectTab(NavigationTab.CART) }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = PureWhite,
                    contentColor = AquaDeep,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    val tabs = listOf(
                        Triple(NavigationTab.HOME, "الرئيسية", Icons.Default.Home),
                        Triple(NavigationTab.PRODUCTS, "المنتجات", Icons.Default.ShoppingBag),
                        Triple(NavigationTab.CART, "السلة", Icons.Default.ShoppingCart),
                        Triple(NavigationTab.SHOWROOM, "المعرض", Icons.Default.PhotoLibrary),
                        Triple(NavigationTab.CONTACT, "المحل والتواصل", Icons.Default.ContactSupport)
                    )

                    tabs.forEach { (tab, label, icon) ->
                        val isSelected = uiState.currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                if (tab == NavigationTab.CART && uiState.cartCount > 0) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = MetallicGold,
                                                contentColor = Color.Black
                                            ) {
                                                Text(
                                                    text = uiState.cartCount.toString(),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    ) {
                                        Icon(imageVector = icon, contentDescription = label)
                                    }
                                } else {
                                    Icon(imageVector = icon, contentDescription = label)
                                }
                            },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = AquaDeep,
                                selectedTextColor = AquaDeep,
                                indicatorColor = AquaContainerLight,
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Tab Content with Animated Transition
                AnimatedContent(
                    targetState = uiState.currentTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tab_animation"
                ) { tab ->
                    when (tab) {
                        NavigationTab.HOME -> HomeScreen(
                            viewModel = viewModel,
                            onNavigateToProducts = { categoryId ->
                                viewModel.selectCategory(categoryId)
                                viewModel.selectTab(NavigationTab.PRODUCTS)
                            },
                            onProductClick = { product ->
                                viewModel.selectProduct(product)
                            }
                        )

                        NavigationTab.PRODUCTS -> ProductsScreen(
                            viewModel = viewModel,
                            onProductClick = { product ->
                                viewModel.selectProduct(product)
                            }
                        )

                        NavigationTab.CART -> CartScreen(
                            viewModel = viewModel,
                            onNavigateToProducts = {
                                viewModel.selectTab(NavigationTab.PRODUCTS)
                            }
                        )

                        NavigationTab.SHOWROOM -> ShowroomScreen(
                            viewModel = viewModel
                        )

                        NavigationTab.CONTACT -> ContactScreen(
                            viewModel = viewModel
                        )
                    }
                }

                // Persistent Floating Contact Bar visible above bottom navigation bar
                // (except when in Cart to allow checkout button visibility)
                if (uiState.currentTab != NavigationTab.CART) {
                    FloatingContactBar(
                        viewModel = viewModel,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
