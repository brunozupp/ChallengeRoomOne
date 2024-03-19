package com.novelitech.challengeroomone.pages.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.novelitech.challengeroomone.pages.categories.components.AddCategoryDialog
import com.novelitech.challengeroomone.pages.categories.components.CategoryTile
import com.novelitech.challengeroomone.pages.products.components.AddProductDialog
import com.novelitech.challengeroomone.ui.components.AppScaffold

@Composable
fun CategoriesPage(
    navController: NavHostController,
    viewModel: CategoriesViewModel
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CategoriesEvent.FetchData)
    }

    if(state.value.isDialogOpened) {
        AddCategoryDialog(state = state.value, onEvent = viewModel::onEvent)
    }

    AppScaffold(
        navController = navController,
        titleTopBar = "Categories",
        onTapFloatingActionButton = {
            viewModel.onEvent(CategoriesEvent.ShowDialog)
        },
    ) {
        if(state.value.categories.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "No category added",
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.value.categories) { category ->

                    CategoryTile(
                        category = category,
                        onDelete = {
                            viewModel.onEvent(CategoriesEvent.DeleteCategory(category))
                        }
                    )
                }
            }
        }
    }
}