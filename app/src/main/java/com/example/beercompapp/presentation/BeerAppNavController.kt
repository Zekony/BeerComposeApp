package com.example.beercompapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beercompapp.presentation.item_detail.ItemDetailScreen
import com.example.beercompapp.presentation.shopping_list.ShoppingListScreen
import com.example.beercompapp.presentation.utils.BeerPage


@Composable
fun BeerAppNavController(
    viewModel: MenuScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getProductsFromDb()
        viewModel.getCartItems()
    }

    val navController = rememberNavController()
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
                onProductItemClick = { navController.navigate("beer_item_detail/${it.UID}") },
                uiState = uiState
            )
        }
        composable(
            route = "beer_item_detail/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )

        ) { entry ->
            ItemDetailScreen(
                id = entry.arguments?.getString("id")!!,
                uiState = uiState,
                onBackPressed = { navController.navigateUp() },)
        }

    }
}

