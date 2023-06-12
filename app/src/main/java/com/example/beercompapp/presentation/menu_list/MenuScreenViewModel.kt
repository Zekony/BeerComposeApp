package com.example.beercompapp.presentation.menu_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.Resource
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
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
    private val cartUseCases: CartUseCases,
    private val repository: ProductAppRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BeerAppUiState())
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
                // проверяем есть ли чтото в датабазе - нужно ли делать запрос на сервер, так как до onCompletion код не доходит
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
        if (_state.value.listOfProducts.isEmpty()) {
            productUseCases.getBeersFromNetworkUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data == null || result.data.isEmpty()) {
                            Log.d(
                                "MSViewModel",
                                "Beer Request returned empty or null ${result.data}"
                            )
                        } else {
                            _state.update {
                                it.copy(
                                    downloadState = DownloadState.Success,
                                    listOfProducts = _state.value.listOfProducts.plus(result.data)
                                )
                            }
                            _state.value.listOfProducts.forEach { repository.addProduct(it) }
                            Log.d(
                                "MSViewModel",
                                "UiState listOfBeer is ${_state.value.listOfProducts.size}"
                            )
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
                        Log.d("MSViewModel", "Beer Request is loading ${result.data}")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getSnacksFromApi() {
        Log.d("MSViewModel", "getSnacksFromApi is called")
        productUseCases.getSnacksFromNetworkUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null || result.data.isEmpty()) {
                        Log.d(
                            "MSViewModel",
                            "Snack Request returned empty or null ${result.data}"
                        )
                    } else {
                        _state.update {
                            it.copy(
                                downloadState = DownloadState.Success,
                                listOfProducts = _state.value.listOfProducts.plus(result.data)
                            )
                        }
                        _state.value.listOfProducts.forEach { repository.addProduct(it) }
                        Log.d(
                            "MSViewModel",
                            "UiState listOfSnacks is ${_state.value.listOfProducts.size}"
                        )
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
            Log.d("MSViewModel", "active user name is ${_state.value.user.login} and password is ${_state.value.user.password}")
            getLikesList()
        }
    }

    //functions for cart button
    fun addToCartFunc(item: CartItem) {
        viewModelScope.launch {
            Log.d("MSViewModel", "addToCartFunc is called")
            cartUseCases.addToCartUseCase(item)
        }
    }

    fun deleteCartItemFunc(item: CartItem) {
        viewModelScope.launch {
            Log.d("MSViewModel", "deleteCartItemFunc is called")
            cartUseCases.deleteCartItemUseCase(item)
        }
    }

    fun updateCartItemFunc(item: CartItem) {
        viewModelScope.launch {
            Log.d("MSViewModel", "updateCartItemFunc is called")
            cartUseCases.updateCartItemUseCase(item)
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            Log.d("MSViewModel", "getCartItems is called")
            cartUseCases.getCartItemsFromDbUseCase()
                .collect { list ->
                    _state.update {
                        it.copy(
                            shoppingCart = list
                        )
                    }
                    Log.d(
                        "MSViewModel",
                        "getCartItems list size is ${list.size} state list is ${state.value.shoppingCart.size}"
                    )
                }
        }
    }
}