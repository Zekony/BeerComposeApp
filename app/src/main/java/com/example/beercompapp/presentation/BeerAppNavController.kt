package com.example.beercompapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beercompapp.presentation.item_detail.ItemDetailScreen
import com.example.beercompapp.presentation.utils.BeerPage


@Composable
fun BeerAppNavController(
    viewModel: MenuScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    val navController = rememberNavController()
    BeerAppNavHost(
        uiState = uiState,
        viewModel = viewModel,
        navController = navController,
    )
}

@Composable
fun BeerAppNavHost(
    uiState: BeerAppUiState,
    viewModel: MenuScreenViewModel,
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            BeerAppPager(
                viewModel = viewModel,
                onCategoryClick = {
                    when (it) {
                        MenuCategory.Beer -> viewModel.updateCurrentPage(BeerPage.Menu, it)
                        MenuCategory.Snacks -> viewModel.updateCurrentPage(BeerPage.Menu, it)
                    }
                },
                onProductItemClick = { navController.navigate("beer_item_detail") },
                onTabPressed = { beerPage: BeerPage ->
                    viewModel.updateCurrentPage(beerPage = beerPage)
                },
                uiState = uiState,
                )
        }
        composable(
            route = "beer_item_detail"

        ) {

        }

    }
}