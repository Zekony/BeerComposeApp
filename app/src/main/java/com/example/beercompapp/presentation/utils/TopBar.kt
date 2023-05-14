package com.example.beercompapp.presentation.utils

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.beercompapp.R

@Composable
fun BeerAppTopBar(
    onButtonClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        navigationIcon = {
            IconButton(onButtonClick)  {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.go_to_authorization_screen)
                )
            }
        },
        modifier = Modifier
    )
}