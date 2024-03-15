package com.novelitech.challengeroomone.pages.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novelitech.challengeroomone.database.daos.CategoryDao
import com.novelitech.challengeroomone.database.entities.CategoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoryDao: CategoryDao,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories = _categories.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(CategoriesState())

    val state = combine(_state, _categories) { state,  categories ->
        state.copy(
            categories = categories,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CategoriesState())

    fun onEvent(event: CategoriesEvent) {
        when(event) {
            is CategoriesEvent.DeleteCategory -> {
                viewModelScope.launch {
                    categoryDao.delete(event.category)
                }
            }
            CategoriesEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isDialogOpened = false
                    )
                }
            }
            CategoriesEvent.SaveCategory -> {

                val nameCategory = state.value.nameCategory

                if(nameCategory.isBlank()) {
                    return
                }

                val category = CategoryEntity(
                    name = nameCategory
                )

                viewModelScope.launch {
                    categoryDao.insert(category)
                }

                _state.update {
                    it.copy(
                        nameCategory = "",
                        isDialogOpened = false,
                    )
                }
            }
            is CategoriesEvent.SetNameCategory -> {
                _state.update {
                    it.copy(
                        nameCategory = event.name
                    )
                }
            }
            CategoriesEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isDialogOpened = true
                    )
                }
            }

            CategoriesEvent.FetchData -> {
                viewModelScope.launch {

                    categoryDao.getAll().collectLatest { list ->
                        _categories.update {
                            list
                        }
                    }
                }
            }
        }
    }
}