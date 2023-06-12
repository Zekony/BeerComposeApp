package com.example.beercompapp.presentation.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beercompapp.presentation.authorization_screen.authorization.AuthScreen
import com.example.beercompapp.presentation.authorization_screen.registration.RegistrationScreen
import com.example.beercompapp.presentation.item_detail.ItemDetailScreen


@Composable
fun BeerAppNavController(
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            BeerAppPager(
                navController = navController
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
                onBackPressed = { navController.navigateUp() },
            )
        }
        composable(
            route = "authorization_screen"
        ) {
            AuthScreen(
                onBackPressed = navController::navigateUp,
                toMainMenu = { navController.navigate("main") },
                toRegistrationScreen = { navController.navigate("registration_screen") })
        }
        composable(
            route = "registration_screen"
        ) {
            RegistrationScreen(
                onBackPressed = navController::navigateUp,
                toAuthorizationScreen = { navController.navigate("authorization_screen") })
        }
    }
}

