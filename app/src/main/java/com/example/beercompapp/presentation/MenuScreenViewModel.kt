package com.example.beercompapp.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.common.Resource
import com.example.beercompapp.domain.use_cases.ProductUseCases
import com.example.beercompapp.presentation.utils.BeerPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val UseCases: ProductUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(BeerAppUiState())
    val state = _state.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        Log.d("MSViewModel", "getProducts method is called")
        UseCases.getBeersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null || result.data.isEmpty()) {
                        Log.d("MSViewModel", "Beer Request returned empty or null ${result.data}")
                    } else {
                        _state.update {
                            it.copy(downloadState = DownloadState.Success, listOfBeer = result.data)
                        }
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
                            error = result.message ?: "An unexpected error occurred, please try again",
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
        UseCases.getSnacksUseCase().onEach { result ->
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
                            error = result.message ?: "An unexpected error occurred, please try again",
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