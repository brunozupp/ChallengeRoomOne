package com.novelitech.challengeroomone.ui.navigation

enum class AppPages {
    HOME,
    CATEGORIES,
    PRODUCTS,
}

sealed class NavigationItem(val route: String) {
    object Home: NavigationItem(AppPages.HOME.name)
    object Categories: NavigationItem(AppPages.CATEGORIES.name)
    object Products: NavigationItem(AppPages.PRODUCTS.name)
}