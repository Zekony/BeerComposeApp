package com.example.beercompapp.presentation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.beercompapp.R
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.main_screen.BeerAppMainScreen
import com.example.beercompapp.presentation.menu_list.BeerAppMenuScreen
import com.example.beercompapp.presentation.shopping_list.ShoppingListBottomBar
import com.example.beercompapp.presentation.shopping_list.ShoppingListScreen
import com.example.beercompapp.presentation.utils.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerAppPager(
    modifier: Modifier = Modifier,
    uiState: BeerAppUiState,
    viewModel: MenuScreenViewModel,
    onCategoryClick: (MenuCategory) -> Unit,
    onProductItemClick: (ProductItem) -> Unit,
) {
    val navigationList = listOf(
        NavigationItemContent(
            pageName = BeerPage.MainScreen,
            icon = Icons.Default.Inbox,
            text = stringResource(id = R.string.main_screen)
        ),
        NavigationItemContent(
            pageName = BeerPage.Menu,
            icon = Icons.Default.LocalDrink,
            text = stringResource(id = R.string.beer_menu)
        ),
        NavigationItemContent(
            pageName = BeerPage.Shopping,
            icon = Icons.Default.ShoppingCart,
            text = stringResource(id = R.string.shopping)
        ),
        NavigationItemContent(
            pageName = BeerPage.Else,
            icon = Icons.Default.ExpandMore,
            text = stringResource(id = R.string.elsee)
        )
    )
    val shoppingCartButtonHelper = object : CartButtonHelper {
        override fun addToCart(cartItem: CartItem) {
            viewModel.addToCart(cartItem)
        }

        override fun updateCartItem(cartItem: CartItem) {
            viewModel.updateCartItem(cartItem)
        }

        override fun deleteCartItem(cartItem: CartItem) {
            viewModel.deleteCartItem(cartItem)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BeerAppTopBar(
                onButtonClick = {
                    if (uiState.currentTab == BeerPage.Shopping) {
                        viewModel.updateCurrentPage(BeerPage.MainScreen)
                    } else {
                        TODO()
                    }
                }, icon = if (uiState.currentTab == BeerPage.Shopping) {
                    Icons.Default.ArrowBack
                } else {
                    Icons.Default.Downloading
                }
            )
        },
        bottomBar = {
            if (uiState.currentTab == BeerPage.Shopping) {
                ShoppingListBottomBar(sum = uiState.shoppingCart.sumOf { it.price * it.amount })
            } else {
                BeerAppBottomNavigationBar(
                    onTabPressed = { beerPage: BeerPage ->
                        viewModel.updateCurrentPage(beerPage = beerPage)
                    },
                    currentPage = uiState.currentTab,
                    navigationList = navigationList
                )
            }
        }
    ) {
        when (uiState.currentTab) {
            BeerPage.MainScreen -> {
                BeerAppMainScreen(
                    onCategoryClick = onCategoryClick,
                )
            }
            BeerPage.Menu -> {
                BeerAppMenuScreen(
                    uiState = uiState,
                    onErrorButtonClick = viewModel::getProductsFromDb,
                    onProductItemClick = onProductItemClick,
                    onFabClick = { viewModel.updateProductItem(it) },
                    cartHelper = shoppingCartButtonHelper
                )
            }
            BeerPage.Shopping -> {
                ShoppingListScreen(
                    uiState = uiState,
                    cartHelper = shoppingCartButtonHelper
                )
            }
            BeerPage.Else -> {

            }
        }
    }
}

/*val pagerState = rememberPagerState()

LaunchedEffect(pagerState.currentPage) {
    onPageChange(pages[pagerState.currentPage])
    viewModel.updateCurrentPage(
        when (pagerState.currentPage) {
            0 -> BeerPage.MainScreen
            1 -> BeerPage.BeerMenu
            2 -> BeerPage.SnackMenu
            3 -> BeerPage.Shopping
            4 -> BeerPage.Else
            else -> BeerPage.MainScreen
        }
    )
}

HorizontalPager(
    pageCount = pages.size,
    state = pagerState,
    verticalAlignment = Alignment.Top
) { index ->
    when (pages[index]) {
        BeerPage.MainScreen -> {
            BeerAppMainScreen(
                uiState = uiState,
                onTabPressed = onTabPressed,
                onCategoryClick = onCategoryClick,
                navigationItemContentList = navigationList
            )
        }
        BeerPage.BeerMenu -> {

            BeerAppMenuScreen(
                listOfProducts = uiState.listOfProducts,
                uiState = uiState,
                onProductItemClick = {},
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationList
            )

        }
        BeerPage.SnackMenu -> {
            BeerAppMenuScreen(
                listOfProducts = uiState.listOfProducts,
                uiState = uiState,
                onProductItemClick = {},
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationList
            )
        }
        BeerPage.Shopping -> {

        }
        BeerPage.Else -> {

        }
    }

}*/






