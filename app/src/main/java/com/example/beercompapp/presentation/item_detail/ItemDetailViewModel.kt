package com.example.beercompapp.presentation.item_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.data.entities.relations.UserProductItemLikes
import com.example.beercompapp.domain.use_cases.cart_item_usecases.CartUseCases
import com.example.beercompapp.domain.use_cases.likes_usecases.LikesUseCases
import com.example.beercompapp.domain.use_cases.products_usecases.ProductUseCases
import com.example.beercompapp.domain.use_cases.user_usecases.UserUseCases
import com.example.beercompapp.presentation.utils.CartButtonHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val likesUseCases: LikesUseCases,
    private val userUseCases: UserUseCases,
    private val cartUseCases: CartUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getActiveUser()
        getCartItems()
    }

    private fun getActiveUser() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(user = userUseCases.getActiveUserUseCase()))
        }
        getLikesList()
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            val item = productUseCases.getProductByIdFromDbUseCase(id)
            _uiState.update { state ->
                state.copy(
                    product = state.product.plus(item.first()),
                    downloadState = DownloadState.Success
                )
            }

        }
    }

    private fun getLikesList() {
        viewModelScope.launch {
            likesUseCases.getUserLikesUseCase(_uiState.value.user.phoneNumber)
                .collect { list ->
                    _uiState.update { state ->
                        state.copy(
                            userLikes = if (list == null || list.menuItems == null) {
                                emptyList()
                            } else {
                                Log.d("MSViewModel", "${list.menuItems.size}")
                                list.menuItems
                            }
                        )
                    }
                }
        }
    }

    fun addLike(item: ProductItem) {
        viewModelScope.launch {
            val isItemLiked = likesUseCases.isItemLikedUseCase(item.UID)
            if (isItemLiked) {
                likesUseCases.removeLikeUseCase(item.UID, _uiState.value.user.phoneNumber)
            } else {
                likesUseCases.addLikeUseCase(
                    userProductItem = UserProductItemLikes(
                        phoneNumber = _uiState.value.user.phoneNumber,
                        UID = item.UID
                    )
                )
            }
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            Log.d("IDViewModel", "getCartItems is called")
            cartUseCases.getCartItemsFromDbUseCase()
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            shoppingCart = list
                        )
                    }
                    Log.d("IDViewModel", "getCartItems $list")
                }
        }
    }

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
        Log.d("IDViewModel", "addToCart is called")
        viewModelScope.launch {
            cartUseCases.addToCartUseCase(item)
        }
    }

    fun deleteCartItemFunc(item: CartItem) {
        Log.d("IDViewModel", "deleteCartItem is called")
        viewModelScope.launch {
            cartUseCases.deleteCartItemUseCase(item)
        }
    }

    fun updateCartItemFunc(item: CartItem) {
        Log.d("IDViewModel", "updateCartItem is called")
        viewModelScope.launch {
            cartUseCases.updateCartItemUseCase(item)
        }
    }
}