package com.novelitech.challengeroomone.pages.categories

import com.novelitech.challengeroomone.database.entities.CategoryEntity

data class CategoriesState(
    val categories: List<CategoryEntity> = emptyList(),
    val nameCategory: String = "",
    val isDialogOpened: Boolean = false,
)
