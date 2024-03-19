package com.novelitech.challengeroomone.pages.products

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.novelitech.challengeroomone.pages.products.components.AddProductDialog
import com.novelitech.challengeroomone.pages.products.components.ProductTile
import com.novelitech.challengeroomone.ui.components.AppScaffold

@Composable
fun ProductsPage(
    viewModel: ProductsViewModel,
    navController: NavHostController,
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProductsEvent.FetchData)
    }

    if(state.value.isDialogOpened) {
        AddProductDialog(state = state.value, onEvent = viewModel::onEvent)
    }

    AppScaffold(
        navController = navController,
        titleTopBar = "Products",
        onTapFloatingActionButton = {
            viewModel.onEvent(ProductsEvent.ShowDialog)
        },
    ) {
        LazyColumn {
            items(state.value.products) { product ->
                ProductTile(
                    product = product,
                    onDelete = {
                        viewModel.onEvent(ProductsEvent.DeleteProduct(product = product))
                    }
                )
            }
        }
    }
}