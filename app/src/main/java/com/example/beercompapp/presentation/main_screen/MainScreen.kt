package com.example.beercompapp.presentation.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beercompapp.R
import com.example.beercompapp.presentation.MenuCategory
import com.example.beercompapp.presentation.ui.theme.BeerCompAppTheme


@Composable
fun BeerAppMainScreen(
    onCategoryClick: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    BeerAppMainScreenContent(
        onCategoryClick = onCategoryClick,
        modifier = modifier
    )
}


@Composable
private fun BeerAppMainScreenContent(
    onCategoryClick: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val imagesList = listOf(R.drawable.beer, R.drawable.snacks2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(6.dp, top = 60.dp, bottom = 70.dp)
    ) {
        Text(text = stringResource(id = R.string.categories), style = MaterialTheme.typography.h1)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(imagesList.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = imagesList[item]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxSize()
                            .clickable {
                                onCategoryClick(
                                    when (imagesList[item]) {
                                        R.drawable.beer -> MenuCategory.Beer
                                        R.drawable.snacks2 -> MenuCategory.Snacks
                                        else -> MenuCategory.Beer
                                    }
                                )
                            }
                    )
                    Text(
                        text = stringResource(
                            id = when (imagesList[item]) {
                                R.drawable.beer -> R.string.beverages
                                R.drawable.snacks2 -> R.string.snack_menu
                                else -> R.string.beverages
                            }
                        ),
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp)
                    )
                }
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


