package com.example.bioskopku

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bioskopku.ui.navigation.Navigation
import com.example.bioskopku.ui.navigation.Screen
import com.example.bioskopku.ui.screen.about.AboutScreen
import com.example.bioskopku.ui.screen.cart.CartScreen
import com.example.bioskopku.ui.screen.detail.DetailScreen
import com.example.bioskopku.ui.screen.home.HomeScreen
import com.example.bioskopku.ui.theme.BioskopkuTheme

@Composable
fun BioskopkuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFilm.route) {
                BottomBar(navController)
        }
    },
        modifier = modifier
    ) {innerPadding ->
        NavHost(navController = navController,
            startDestination = Screen.Home.route ,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(navigateToDetail = { filmId ->
                    navController.navigate(Screen.DetailFilm.createRoute(filmId))
                })
            }
            composable(Screen.Cart.route){
                val context = LocalContext.current
                CartScreen(onOrderButtonClicked = {message ->
                    shareOrder(context,message)
                })
            }
            composable(Screen.About.route){
                AboutScreen()
            }
            composable(
                route = Screen.DetailFilm.route,
                arguments = listOf(navArgument("filmId") { type = NavType.LongType}),
            ){
                val id = it.arguments?.getLong("filmId")?: -1L
                DetailScreen(filmId = id, navigateBack = { navController.navigateUp() },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }

    }
}

private fun shareOrder(context: Context, summary : String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Tiket Nonton")
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(intent, context.getString(R.string.tiket_bioskopku))
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    BottomNavigation( modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            Navigation(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            Navigation(
                title = stringResource(R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            Navigation(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon,
                            contentDescription = item.title)
                    },
                    label = { Text(item.title)},
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BioskopkuAppPreview(){
    BioskopkuTheme {
        BioskopkuApp()
    }
}