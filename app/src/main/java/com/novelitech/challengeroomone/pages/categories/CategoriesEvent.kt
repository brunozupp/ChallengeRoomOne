package com.novelitech.challengeroomone.pages.categories

import com.novelitech.challengeroomone.database.entities.CategoryEntity

sealed interface CategoriesEvent {

    object SaveCategory: CategoriesEvent

    data class SetNameCategory(val name: String): CategoriesEvent

    data class SetDescriptionCategory(val description: String): CategoriesEvent

    data class DeleteCategory(val category: CategoryEntity): CategoriesEvent

    object ShowDialog: CategoriesEvent

    object HideDialog: CategoriesEvent

    object FetchData: CategoriesEvent
}