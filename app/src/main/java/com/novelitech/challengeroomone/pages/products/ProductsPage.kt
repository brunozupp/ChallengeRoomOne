package com.novelitech.challengeroomone.pages.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.novelitech.challengeroomone.pages.categories.CategoriesEvent
import com.novelitech.challengeroomone.pages.categories.components.AddCategoryDialog
import com.novelitech.challengeroomone.pages.products.components.AddProductDialog
import com.novelitech.challengeroomone.pages.products.components.ProductTile
import com.novelitech.challengeroomone.ui.theme.BlueApp

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BlueApp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Products",
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(ProductsEvent.ShowDialog)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new category")
            }
        }
    ) { innerPadding ->
       Box(
           modifier = Modifier
               .padding(innerPadding)
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
}