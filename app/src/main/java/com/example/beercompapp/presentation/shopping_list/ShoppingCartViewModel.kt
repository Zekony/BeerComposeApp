package com.example.beercompapp.presentation.shopping_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.use_cases.cart_item_usecases.CartUseCases
import com.example.beercompapp.presentation.menu_list.BeerAppUiState
import com.example.beercompapp.presentation.utils.CartButtonHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases
) : ViewModel() {

    init {
        getCartItems()
    }

    private val _state = MutableStateFlow(BeerAppUiState())
    val state = _state.asStateFlow()

    val shoppingCartButtonHelper = object : CartButtonHelper {
        override fun addToCart(cartItem: CartItem) {
            addToCartFunc(cartItem)
        }

        override fun updateCartItem(cartItem: CartItem) {
            updateCartItemFunc(cartItem)
        }

        override fun deleteCartItem(cartItem: CartItem) {
            deleteCartItemFunc(cartItem)
        }
    }

    fun addToCartFunc(item: CartItem) {
        viewModelScope.launch {
            cartUseCases.addToCartUseCase(item)
        }
    }

    fun deleteCartItemFunc(item: CartItem) {
        viewModelScope.launch {
            cartUseCases.deleteCartItemUseCase(item)
        }
    }

    fun updateCartItemFunc(item: CartItem) {
        viewModelScope.launch {
            cartUseCases.updateCartItemUseCase(item)
        }
    }

    fun getCartItems() {
        Log.d("ShopCartViewModel", "getCartItems is called")
        viewModelScope.launch {
            cartUseCases.getCartItemsFromDbUseCase().collect { list ->
                _state.update {
                    it.copy(
                        shoppingCart = list
                    )
                }
            }
        }
    }
}