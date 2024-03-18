package com.novelitech.challengeroomone.pages.products

import com.novelitech.challengeroomone.database.entities.ProductEntity

data class ProductsState(
    val products: List<ProductEntity>,
    val isDialogOpened: Boolean = false,
    val productName: String = "",
    val productEnabled: Boolean = true,
    val productCategoryId: Int,
)