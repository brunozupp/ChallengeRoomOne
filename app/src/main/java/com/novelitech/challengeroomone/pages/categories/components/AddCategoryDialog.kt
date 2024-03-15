package com.novelitech.challengeroomone.pages.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.novelitech.challengeroomone.pages.categories.CategoriesEvent
import com.novelitech.challengeroomone.pages.categories.CategoriesState
import com.novelitech.challengeroomone.ui.theme.BlueApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryDialog(
    state: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(CategoriesEvent.HideDialog) },
        confirmButton = {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueApp,
                    ),
                    onClick = {
                        onEvent(CategoriesEvent.SaveCategory)
                    }
                ) {
                    Text(text = "Save")
                }
            }
        },
        title = {
            Text(text = "Add category")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.nameCategory,
                    onValueChange = {
                        onEvent(CategoriesEvent.SetNameCategory(it))
                    },
                    label = {
                        Text(text = "Name")
                    }
                )
            }
        }
    )
}