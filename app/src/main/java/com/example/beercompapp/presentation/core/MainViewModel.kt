package com.example.beercompapp.presentation.core


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beercompapp.R
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.domain.use_cases.cart_item_usecases.CartUseCases
import com.example.beercompapp.presentation.utils.BeerPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartUseCases: CartUseCases
) : ViewModel() {

    private val _navState = MutableStateFlow(NavigationState())
    val navState = _navState.asStateFlow()

    private var _shoppingCart = MutableStateFlow<List<CartItem>>(emptyList())
    val shoppingCart = _shoppingCart.asStateFlow()

    init {
        getCartItems()
    }
    private fun getCartItems() {
        viewModelScope.launch {
            cartUseCases.getCartItemsFromDbUseCase().collect { list ->
                _shoppingCart.update {
                    list
                }
            }
        }
    }

    fun emptyCart(context: Context) {
        viewModelScope.launch {
            cartUseCases.emptyCartUseCase()
        }
        showToast(context.getString(R.string.thanks_for_order), context)
    }



    fun updateCurrentPage(beerPage: BeerPage, menuCategory: MenuCategory? = null) {
        if (_navState.value.currentTab == BeerPage.Menu && beerPage == BeerPage.Menu) {
            return
        }
        if (beerPage == BeerPage.Menu) {
            when (menuCategory) {
                MenuCategory.Beer -> _navState.update { it.copy(menuCategory = MenuCategory.Beer) }
                MenuCategory.Snacks -> _navState.update { it.copy(menuCategory = MenuCategory.Snacks) }
                else -> _navState.update { it.copy(menuCategory = MenuCategory.Beer) }
            }
        }

        _navState.update {
            it.copy(
                currentTab = beerPage
            )
        }
        Log.d(
            "MSViewModel",
            "Current tab has been updated now it's ${_navState.value.currentTab} and MenuCategory is ${_navState.value.menuCategory}"
        )
    }

    private fun showToast(text: String, context: Context) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}