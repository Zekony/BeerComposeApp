package com.example.beercompapp.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.Resource
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.domain.repository.ProductAppRepository
import com.example.beercompapp.domain.use_cases.ProductUseCases
import com.example.beercompapp.presentation.utils.BeerPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val useCases: ProductUseCases,
    private val repository: ProductAppRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BeerAppUiState())
    val state = _state.asStateFlow()

    fun getProductsFromDb() {
        Log.d("MSViewModel", "getProductsFromDb is called")
        viewModelScope.launch {
            val prod = useCases.getProductsFromDbUseCase().first()
            if (prod.isEmpty()) {
                getSnacksFromApi()
                getBeerFromApi()
            }
            useCases.getProductsFromDbUseCase()
                .onCompletion {
                    Log.d(
                        "MSViewModel",
                        "List's sizes from db are ${_state.value.listOfBeer.size} ${_state.value.listOfSnacks.size}"
                    )
                    if (_state.value.listOfBeer.isEmpty()) {
                        getBeerFromApi()
                    }
                    if (_state.value.listOfSnacks.isEmpty()) {
                        getSnacksFromApi()
                    }
                }
                .collect { list ->
                    _state.update { state ->
                        state.copy(
                            downloadState = if (list.isEmpty()) {
                                return@collect
                            } else {
                                DownloadState.Success
                            },
                            listOfSnacks = list.filter { it.category == MenuCategory.Snacks })
                    }
                    _state.update { state ->
                        state.copy(
                            downloadState = if (list.isEmpty()) {
                                return@collect
                            } else {
                                DownloadState.Success
                            },
                            listOfBeer = list.filter { it.category == MenuCategory.Beer })
                    }
                }
        }
    }

    private fun getBeerFromApi() {
        Log.d("MSViewModel", "getBeerFromApi is called")
        if (_state.value.listOfBeer.isEmpty()) {
            useCases.getBeersFromNetworkUseCase().onEach { result ->
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
                                    listOfBeer = _state.value.listOfBeer.plus(result.data)
                                )
                            }
                            _state.value.listOfBeer.forEach { repository.addProduct(it) }
                            Log.d(
                                "MSViewModel",
                                "UiState listOfBeer is ${_state.value.listOfBeer.size}"
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
        if (_state.value.listOfSnacks.isEmpty()) {
            useCases.getSnacksFromNetworkUseCase().onEach { result ->
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
                                    listOfSnacks = result.data
                                )
                            }
                            _state.value.listOfSnacks.forEach { repository.addProduct(it) }
                            Log.d(
                                "MSViewModel",
                                "UiState listOfSnacks is ${_state.value.listOfSnacks.size}"
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
    }

    //functions for cart button
    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            useCases.addToCartUseCase(item)
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch {
            useCases.deleteCartItemUseCase(item)
        }
    }

    fun updateCartItem(item: CartItem) {
        viewModelScope.launch {
            useCases.updateCartItemUseCase(item)
        }
    }

    fun updateProductItem(item: ProductItem) {
        Log.d("MSViewModel", "updateProductItem is called ${item.isFavorite}")
        viewModelScope.launch {
            useCases.updateProductInDbUseCase(item)
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            useCases.getCartItemsFromDbUseCase().collect { list ->
                _state.update {
                    it.copy(
                        shoppingCart = list
                    )
                }
            }
        }
    }

    fun updateCurrentPage(beerPage: BeerPage, menuCategory: MenuCategory? = null) {
        if (_state.value.currentTab == BeerPage.Menu && beerPage == BeerPage.Menu) {
            return
        }
        if (beerPage == BeerPage.Menu) {
            when (menuCategory) {
                MenuCategory.Beer -> _state.update { it.copy(menuCategory = MenuCategory.Beer) }
                MenuCategory.Snacks -> _state.update { it.copy(menuCategory = MenuCategory.Snacks) }
                else -> _state.update { it.copy(menuCategory = MenuCategory.Beer) }
            }
        }

        _state.update {
            it.copy(
                currentTab = beerPage
            )
        }
        Log.d(
            "MSViewModel",
            "Current tab has been updated now it's ${_state.value.currentTab} and MenuCategory is ${_state.value.menuCategory}"
        )
    }
}