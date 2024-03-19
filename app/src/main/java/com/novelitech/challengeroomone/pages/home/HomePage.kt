package com.novelitech.challengeroomone.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.novelitech.challengeroomone.ui.components.AppScaffold
import com.novelitech.challengeroomone.ui.navigation.NavigationItem

@Composable
fun HomePage(navController: NavHostController) {

    val routes = listOf<String>(
        NavigationItem.Products.route,
        NavigationItem.Categories.route,
    )

    AppScaffold(
        navController = navController,
        showFloatingActionButton = false,
        titleTopBar = "Home"
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(routes) { route ->
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .align(Alignment.Center)
                            .background(Color(0xFFbfc9d9))
                            .clickable {
                                navController.navigate(route)
                            }
                    ) {
                        Text(
                            text = route
                                .lowercase()
                                .replaceFirstChar {it.uppercase() },
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}