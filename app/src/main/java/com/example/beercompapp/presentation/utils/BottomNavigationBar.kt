package com.example.beercompapp.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun BeerAppBottomNavigationBar(
    currentPage: BeerPage,
    onTabPressed: (BeerPage) -> Unit,
    navigationList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colors.primary,
        modifier = modifier.fillMaxWidth()
    ) {
        for (navItem in navigationList) {
            val selected = currentPage == navItem.pageName
            NavigationBarItem(
                selected = currentPage == navItem.pageName,
                onClick = { onTabPressed(navItem.pageName) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text,
                    )
                },
                colors = NavigationBarItemDefaults.colors
                    (indicatorColor = MaterialTheme.colors.onSurface)
            )
        }
    }
}

data class NavigationItemContent(
    val pageName: BeerPage,
    val icon: ImageVector,
    val text: String
)

enum class BeerPage {
    MainScreen, Menu, Shopping, Else
}