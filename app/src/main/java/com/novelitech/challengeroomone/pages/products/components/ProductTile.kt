package com.novelitech.challengeroomone.pages.products.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novelitech.challengeroomone.database.entities.CategoryEntity
import com.novelitech.challengeroomone.database.entities.ProductEntity

@Composable
fun ProductTile(
    product: ProductEntity,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                )
                Text(
                    text = "Enabled: ${product.enabled}",
                    fontSize = 16.sp,
                )
                Text(
                    text = "ID: ${product.id}",
                    fontSize = 16.sp,
                )
                Text(
                    text = "Category ID: ${product.categoryId}",
                    fontSize = 12.sp,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete product",
                modifier = Modifier.clickable { onDelete() }
            )
        }
    }
}