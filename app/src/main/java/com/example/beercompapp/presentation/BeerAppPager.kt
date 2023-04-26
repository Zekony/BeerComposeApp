package com.example.beercompapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.beercompapp.R
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.main_screen.BeerAppMainScreen
import com.example.beercompapp.presentation.menu_list.BeerAppMenuScreen
import com.example.beercompapp.presentation.utils.BeerAppBottomNavigationBar
import com.example.beercompapp.presentation.utils.BeerPage
import com.example.beercompapp.presentation.utils.NavigationItemContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerAppPager(
    uiState: BeerAppUiState,
    viewModel: MenuScreenViewModel,
    onCategoryClick: (MenuCategory) -> Unit,
    onProductItemClick: (ProductItem) -> Unit,
    onTabPressed: (BeerPage) -> Unit,
    pages: Array<BeerPage> = BeerPage.values(),
    modifier: Modifier = Modifier
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

    Scaffold(
        modifier = modifier,
        topBar = { BeerAppTopBar(onButtonClick = { /*TODO*/ })},
        bottomBar = {
            BeerAppBottomNavigationBar(
                onTabPressed = onTabPressed,
                currentPage = uiState.currentTab,
                navigationList = navigationList
            )
        }

    ) { innerPadding ->
        when (uiState.currentTab) {
            BeerPage.MainScreen -> {
                BeerAppMainScreen(
                    uiState = uiState,
                    onCategoryClick = onCategoryClick,
                    modifier = modifier.padding(innerPadding)
                )
            }
            BeerPage.Menu -> {

                BeerAppMenuScreen(
                    listOfBeer = uiState.listOfBeer,
                    listOfSnacks = uiState.listOfSnacks,
                    uiState = uiState,
                    onErrorButtonClick = { viewModel.getProducts() },
                    onProductItemClick = onProductItemClick,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationList
                )

            }
            BeerPage.Shopping -> {

            }
            BeerPage.Else -> {

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
}
@Composable
fun BeerAppTopBar(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Downloading,
                    contentDescription = stringResource(R.string.go_to_authorization_screen)
                )
            }
        },
        modifier = Modifier
    )
}




