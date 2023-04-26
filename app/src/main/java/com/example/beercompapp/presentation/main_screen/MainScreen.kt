package com.example.beercompapp.presentation.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beercompapp.R
import com.example.beercompapp.presentation.BeerAppUiState
import com.example.beercompapp.presentation.MenuCategory
import com.example.beercompapp.presentation.ui.theme.BeerCompAppTheme
import com.example.beercompapp.presentation.utils.BeerPage
import com.example.beercompapp.presentation.utils.NavigationItemContent


@Composable
fun BeerAppMainScreen(
    uiState: BeerAppUiState,
    onCategoryClick: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    BeerAppMainScreenContent(
        onCategoryClick = onCategoryClick,
    )
}



@Composable
private fun BeerAppMainScreenContent(
    onCategoryClick: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val imagesList = listOf(R.drawable.beer, R.drawable.snacks)
    Column() {
        Spacer(modifier = Modifier.height(400.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Bottom,
            //horizontalArrangement = Arrangement.spacedBy(4.dp),

        ) {
            items(imagesList.size) { item ->
                Image(
                    painter = painterResource(id = imagesList[item]),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize()
                        .clickable {
                            onCategoryClick(
                                if (imagesList[item] == R.drawable.beer) {
                                    MenuCategory.Beer
                                } else {
                                    MenuCategory.Snacks
                                }
                            )
                        }
                )
            }
        }
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    BeerCompAppTheme() {
        Surface() {
            BeerAppMainScreenContent(onCategoryClick = {})
        }

    }
}
