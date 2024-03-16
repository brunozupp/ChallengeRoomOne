package com.novelitech.challengeroomone.pages.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.novelitech.challengeroomone.pages.categories.components.AddCategoryDialog
import com.novelitech.challengeroomone.pages.categories.components.CategoryTile
import com.novelitech.challengeroomone.ui.theme.BlueApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesPage(
    navController: NavHostController,
    viewModel: CategoriesViewModel
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CategoriesEvent.FetchData)
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
                    text = "Categories",
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(CategoriesEvent.ShowDialog)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new category")
            }
        }
    ) { paddingValues ->

        if(state.value.isDialogOpened) {
            AddCategoryDialog(state = state.value, onEvent = viewModel::onEvent)
        }

        Box(
            modifier = Modifier
                .padding(paddingValues)
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
}