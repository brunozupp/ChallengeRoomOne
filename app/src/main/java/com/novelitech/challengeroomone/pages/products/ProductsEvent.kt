package com.novelitech.challengeroomone.pages.products

import com.novelitech.challengeroomone.database.entities.ProductEntity

sealed interface ProductsEvent {

    object SaveProduct: ProductsEvent

    data class SetProductName(val name: String): ProductsEvent
    data class SetProductEnabled(val enabled: Boolean): ProductsEvent
    data class SetProductCategoryId(val categoryId: Int): ProductsEvent

    data class DeleteProduct(val product: ProductEntity): ProductsEvent

    object ShowDialog: ProductsEvent

    object HideDialog: ProductsEvent

    object FetchData: ProductsEvent
}