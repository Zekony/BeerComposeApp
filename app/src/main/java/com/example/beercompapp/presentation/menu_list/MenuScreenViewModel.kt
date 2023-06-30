package com.example.beercompapp.presentation.menu_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.Resource
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.use_cases.cart_item_usecases.CartUseCases
import com.example.beercompapp.domain.use_cases.likes_usecases.LikesUseCases
import com.example.beercompapp.domain.use_cases.products_usecases.ProductUseCases
import com.example.beercompapp.domain.use_cases.user_usecases.UserUseCases
import com.example.beercompapp.presentation.utils.CartButtonHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val userUseCases: UserUseCases,
    private val likesUseCases: LikesUseCases,
    private val cartUseCases: CartUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MenuScreenUiState())
    val state = _state.asStateFlow()

    init {
        checkForActiveUser()
        getProductsFromDb()
        getCartItems()
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

    fun getProductsFromDb() {
        Log.d("MSViewModel", "getProductsFromDb is called")
        if (_state.value.listOfProducts.isEmpty()) {
            viewModelScope.launch {
                val prod = productUseCases.getProductsFromDbUseCase().first()
                if (prod.isEmpty()) {
                    getSnacksFromApi()
                    getBeerFromApi()
                } else {
                    productUseCases.getProductsFromDbUseCase()
                        .collect { list ->
                            _state.update { state ->
                                state.copy(
                                    downloadState = DownloadState.Success,
                                    listOfProducts = list
                                )
                            }
                        }
                }
            }
        }
    }

    private fun getBeerFromApi() {
        Log.d("MSViewModel", "getBeerFromApi is called")
        productUseCases.getBeersFromNetworkUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null) {
                        _state.update {
                            it.copy(
                                downloadState = DownloadState.Error
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                downloadState = DownloadState.Success,
                                listOfProducts = _state.value.listOfProducts.plus(result.data as List<ProductItem>)
                            )
                        }
                        _state.value.listOfProducts.forEach {
                            productUseCases.addProductToDBUseCase(
                                it
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    Log.d("MSViewModel", "Beer Request returned error ${result.message}")
                    _state.update {
                        it.copy(
                            downloadState = DownloadState.Error
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(downloadState = DownloadState.Loading)
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun getSnacksFromApi() {
        Log.d("MSViewModel", "getSnacksFromApi is called")
        productUseCases.getSnacksFromNetworkUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null) {
                        _state.update {
                            it.copy(
                                downloadState = DownloadState.Error
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                downloadState = DownloadState.Success,
                                listOfProducts = _state.value.listOfProducts.plus(result.data as List<ProductItem>)
                            )
                        }
                        _state.value.listOfProducts.forEach {
                            productUseCases.addProductToDBUseCase(
                                it
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    Log.d("MSViewModel", "Snack Request returned error ${result.message}")
                    _state.update {
                        it.copy(
                            downloadState = DownloadState.Error
                        )
                    }
                }
                is Resource.Loading -> {
                    Log.d("MSViewModel", "Snack Request is loading ${result.data}")
                    _state.update {
                        it.copy(downloadState = DownloadState.Loading)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLikesList() {
        viewModelScope.launch {
            likesUseCases.getUserLikesUseCase(_state.value.user.phoneNumber)
                .collect { list ->
                    _state.update { state ->
                        state.copy(
                            userLikes = if (list == null || list.menuItems == null) {
                                emptyList()
                            } else {
                                Log.d("MSViewModel", "getLikesList size is ${list.menuItems.size}")
                                list.menuItems
                            }
                        )
                    }
                }
        }
    }

    fun likeOrDislike(item: ProductItem) {
        viewModelScope.launch {
            likesUseCases.likeOrDislikeUseCase(item.UID, _state.value.userLikes)
        }
    }

    private fun checkForActiveUser() {
        viewModelScope.launch {
            _state.update { state.value.copy(user = userUseCases.getActiveUserUseCase()) }
            getLikesList()
        }
    }

    //functions for cart button
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

    private fun getCartItems() {
        viewModelScope.launch {
            cartUseCases.getCartItemsFromDbUseCase()
                .collect { list ->
                    _state.update {
                        it.copy(shoppingCart = list)
                    }
                }
        }
    }
}