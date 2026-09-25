package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.room.Room
import androidx.navigation.toRoute
import com.example.data.AppDatabase
import com.example.data.MarketplaceRepository
import com.example.ui.*
import com.example.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

@Serializable object MarketplaceRoute
@Serializable object DashboardRoute
@Serializable object MessagesRoute
@Serializable object ProfileRoute
@Serializable data class ProductDetailRoute(val productId: Int)
@Serializable data class ChatRoute(val orderId: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val db = remember {
                Room.databaseBuilder(context, AppDatabase::class.java, "marketplace.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            val repository = remember { MarketplaceRepository(db.dao()) }
            val viewModel: MarketplaceViewModel = viewModel { MarketplaceViewModel(repository) }

            LaunchedEffect(Unit) {
                viewModel.seedData()
                viewModel.login("user_123") // Mock login
            }

            MyApplicationTheme {
                MainContent(viewModel)
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MarketplaceViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            // Only show bottom bar on top level screens
            val currentRoute = navBackStackEntry?.destination?.route ?: ""
            val showBottomBar = currentRoute.contains("MarketplaceRoute") ||
                               currentRoute.contains("DashboardRoute") ||
                               currentRoute.contains("MessagesRoute") ||
                               currentRoute.contains("ProfileRoute")

            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Storefront, contentDescription = "Market") },
                        label = { Text("Market") },
                        selected = currentRoute.contains("MarketplaceRoute"),
                        onClick = { navController.navigate(MarketplaceRoute) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        selected = currentRoute.contains("DashboardRoute"),
                        onClick = { navController.navigate(DashboardRoute) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ChatBubble, contentDescription = "Messages") },
                        label = { Text("Messages") },
                        selected = currentRoute.contains("MessagesRoute"),
                        onClick = { navController.navigate(MessagesRoute) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        } }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = currentRoute.contains("ProfileRoute"),
                        onClick = { navController.navigate(ProfileRoute) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        } }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MarketplaceRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MarketplaceRoute> {
                MarketplaceScreen(viewModel, onProductClick = { navController.navigate(ProductDetailRoute(it.id)) })
            }
            composable<DashboardRoute> {
                DashboardScreen(viewModel)
            }
            composable<MessagesRoute> {
                MessagesScreen(viewModel, onChatClick = { navController.navigate(ChatRoute(it)) })
            }
            composable<ProfileRoute> {
                ProfileScreen(viewModel)
            }
            composable<ProductDetailRoute> { backStackEntry ->
                val route: ProductDetailRoute = backStackEntry.toRoute()
                ProductDetailScreen(viewModel, route.productId, onBack = { navController.popBackStack() })
            }
            composable<ChatRoute> { backStackEntry ->
                val route: ChatRoute = backStackEntry.toRoute()
                ChatScreen(viewModel, route.orderId, onBack = { navController.popBackStack() })
            }
        }
    }
}
