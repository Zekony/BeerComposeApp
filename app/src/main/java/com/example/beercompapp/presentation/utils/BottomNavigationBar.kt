package com.example.beercompapp.presentation.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.beercompapp.presentation.ui.theme.Gold


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
            NavigationBarItem(
                selected = currentPage == navItem.pageName,
                onClick = { onTabPressed(navItem.pageName) },
                icon = {
                    Column {
                        if (navItem.amount > 0) {
                            BadgedBox(badge = {
                                Badge(backgroundColor = Gold) {
                                    Text(text = navItem.amount.toString())
                                }
                            }
                            ) {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.text,
                                )
                            }
                        } else {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.text,
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors
                    (indicatorColor = MaterialTheme.colors.secondary)
            )
        }
    }
}

data class NavigationItemContent(
    val pageName: BeerPage,
    val icon: ImageVector,
    val text: String,
    val amount: Int
)

enum class BeerPage {
    MainScreen, Menu, Shopping, Profile
}