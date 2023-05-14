package com.example.beercompapp.presentation.item_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.use_cases.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val useCases: ProductUseCases,
) : ViewModel() {


    private val _productItem = MutableStateFlow<ProductItem?>(null)
    val productItem: StateFlow<ProductItem?>
        get() = _productItem

    fun getProductById(id: String) {
        viewModelScope.launch {
            useCases.getProductByIdFromDbUseCase(id).collect { item ->
                _productItem.tryEmit(item)
            }
        }
    }

    fun updateProductItem(item: ProductItem) {
        Log.d("IDViewModel", "updateProductItem is called ${item.isFavorite}")
        viewModelScope.launch {
            useCases.updateProductInDbUseCase(item)
        }
    }

    fun addToCart(item: CartItem) {
        Log.d("IDViewModel", "addToCart is called")
        viewModelScope.launch {
            useCases.addToCartUseCase(item)
        }
    }

    fun deleteCartItem(item: CartItem) {
        Log.d("IDViewModel", "deleteCartItem is called")
        viewModelScope.launch {
            useCases.deleteCartItemUseCase(item)
        }
    }

    fun updateCartItem(item: CartItem) {
        Log.d("IDViewModel", "updateCartItem is called")
        viewModelScope.launch {
            useCases.updateCartItemUseCase(item)
        }
    }
}