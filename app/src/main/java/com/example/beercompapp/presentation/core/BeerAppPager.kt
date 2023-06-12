package com.example.beercompapp.presentation.core

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.beercompapp.R
import com.example.beercompapp.presentation.main_screen.BeerAppMainScreen
import com.example.beercompapp.presentation.menu_list.BeerAppMenuScreen
import com.example.beercompapp.presentation.profile_screen.ProfileScreen
import com.example.beercompapp.presentation.shopping_list.ShoppingListScreen
import com.example.beercompapp.presentation.shopping_list.utils.ShoppingListBottomBar
import com.example.beercompapp.presentation.utils.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BeerAppPager(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val navState = viewModel.navState.collectAsState().value
    val shoppingCart = viewModel.shoppingCart.collectAsState().value

    val context = LocalContext.current
    // amount of items in shopping cart
    val amountOfItems = shoppingCart.sumOf { it.amount }

    val navigationList = listOf(
        NavigationItemContent(
            pageName = BeerPage.MainScreen,
            icon = Icons.Default.Inbox,
            text = stringResource(id = R.string.main_screen),
            amount = 0
        ),
        NavigationItemContent(
            pageName = BeerPage.Menu,
            icon = Icons.Default.LocalDrink,
            text = stringResource(id = R.string.beer_menu),
            amount = 0
        ),
        NavigationItemContent(
            pageName = BeerPage.Shopping,
            icon = Icons.Default.ShoppingCart,
            text = stringResource(id = R.string.shopping),
            amount = amountOfItems
        ),
        NavigationItemContent(
            pageName = BeerPage.Profile,
            icon = Icons.Default.Person,
            text = stringResource(id = R.string.elsee),
            amount = 0
        )
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                BeerAppTopBar(
                    onButtonClick = {
                        if (navState.currentTab == BeerPage.Shopping) {
                            viewModel.updateCurrentPage(BeerPage.MainScreen)
                        }
                    },
                    icon = if (navState.currentTab == BeerPage.Shopping) {
                        Icons.Default.ArrowBack
                    } else {
                        null
                    },
                )
            },
            bottomBar = {
                if (navState.currentTab == BeerPage.Shopping) {
                    ShoppingListBottomBar(
                        sum = shoppingCart.sumOf { it.price * it.amount },
                        onButtonClick = { viewModel.emptyCart(context) })
                } else {
                    BeerAppBottomNavigationBar(
                        onTabPressed = { beerPage: BeerPage ->
                            viewModel.updateCurrentPage(beerPage = beerPage)
                        },
                        currentPage = navState.currentTab,
                        navigationList = navigationList
                    )
                }
            }
        ) {
            when (navState.currentTab) {
                BeerPage.MainScreen -> {
                    BeerAppMainScreen(
                        onCategoryClick = {
                            when (it) {
                                MenuCategory.Beer -> viewModel.updateCurrentPage(BeerPage.Menu, it)
                                MenuCategory.Snacks -> viewModel.updateCurrentPage(
                                    BeerPage.Menu,
                                    it
                                )
                            }
                        },
                    )
                }
                BeerPage.Menu -> {
                    BeerAppMenuScreen(
                        currentCategory = navState.menuCategory,
                        toItemDetailScreen = { navController.navigate("beer_item_detail/${it.UID}") },
                    )
                }
                BeerPage.Shopping -> {
                    ShoppingListScreen()
                }
                BeerPage.Profile -> {
                    ProfileScreen(
                        toMainScreen = { navController.navigate("main") },
                        toAuthorizationScreen = { navController.navigate("authorization_screen") },
                    )
                }
            }

            val pagerState = rememberPagerState()
            val pages = BeerPage.values()

            LaunchedEffect(navState.currentTab) {
                pagerState.scrollToPage(
                    when (navState.currentTab) {
                        BeerPage.MainScreen -> 0
                        BeerPage.Menu -> 1
                        BeerPage.Shopping -> 2
                        BeerPage.Profile -> 3
                    }
                )
            }
            LaunchedEffect(pagerState.currentPage) {
                viewModel.updateCurrentPage(
                    when (pagerState.currentPage) {
                        0 -> BeerPage.MainScreen
                        1 -> BeerPage.Menu
                        2 -> BeerPage.Shopping
                        3 -> BeerPage.Profile
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
                            onCategoryClick = {
                                when (it) {
                                    MenuCategory.Beer -> viewModel.updateCurrentPage(
                                        BeerPage.Menu,
                                        it
                                    )
                                    MenuCategory.Snacks -> viewModel.updateCurrentPage(
                                        BeerPage.Menu,
                                        it
                                    )
                                }
                            }
                        )
                    }
                    BeerPage.Menu -> {
                        BeerAppMenuScreen(
                            currentCategory = navState.menuCategory,
                            toItemDetailScreen = { navController.navigate("beer_item_detail/${it.UID}") },
                        )
                    }
                    BeerPage.Shopping -> {
                        ShoppingListScreen()
                    }
                    BeerPage.Profile -> {
                        ProfileScreen(
                            toMainScreen = { navController.navigate("main") },
                            toAuthorizationScreen = { navController.navigate("authorization_screen") },
                        )
                    }
                }
            }
        }
    }
}






