package com.novelitech.challengeroomone.pages.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novelitech.challengeroomone.database.daos.ProductDao
import com.novelitech.challengeroomone.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products = _products.stateIn(viewModelScope, SharingStarted.WhileSubscribed(0L), emptyList())

    private val _state = MutableStateFlow(ProductsState())

    val state = combine(_products, _state) { products, state ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProductsState())

    fun onEvent(event: ProductsEvent) {
        when(event) {
            is ProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productDao.delete(event.product)
                }
            }
            ProductsEvent.FetchData -> {
                viewModelScope.launch {
                    productDao.getAll().collectLatest { list ->
                        _products.update {
                            list
                        }
                    }
                }
            }
            ProductsEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isDialogOpened = false
                    )
                }
            }
            ProductsEvent.SaveProduct -> {

                val name = state.value.productName
                val enabled = state.value.productEnabled
                val categoryId = state.value.productCategoryId

                if(name.isBlank() || categoryId == 0) {
                    return
                }

                val product = ProductEntity(
                    name = name,
                    enabled = enabled,
                    categoryId = categoryId,
                )

                viewModelScope.launch {
                    productDao.insert(product)
                }

                _state.update {
                    it.copy(
                        productName = "",
                        productEnabled = true,
                        productCategoryId = 0,
                        isDialogOpened = false,
                    )
                }
            }
            is ProductsEvent.SetProductCategoryId -> {
                _state.update {
                    it.copy(
                        productCategoryId = event.categoryId
                    )
                }
            }
            is ProductsEvent.SetProductEnabled -> {
                _state.update {
                    it.copy(
                        productEnabled = event.enabled
                    )
                }
            }
            is ProductsEvent.SetProductName -> {
                _state.update {
                    it.copy(
                        productName = event.name
                    )
                }
            }
            ProductsEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isDialogOpened = true
                    )
                }
            }
        }
    }

}