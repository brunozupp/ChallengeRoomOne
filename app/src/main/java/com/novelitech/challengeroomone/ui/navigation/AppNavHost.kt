package com.novelitech.challengeroomone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.novelitech.challengeroomone.pages.categories.CategoriesPage
import com.novelitech.challengeroomone.pages.categories.CategoriesViewModel
import com.novelitech.challengeroomone.pages.home.HomePage
import com.novelitech.challengeroomone.pages.products.ProductsPage

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
    categoriesViewModel: CategoriesViewModel,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(NavigationItem.Home.route) {
            HomePage(navController = navController)
        }
        composable(NavigationItem.Categories.route) {
            CategoriesPage(
                viewModel = categoriesViewModel,
                navController = navController,
            )
        }
        composable(NavigationItem.Products.route) {
            ProductsPage(navController = navController)
        }
    }
}