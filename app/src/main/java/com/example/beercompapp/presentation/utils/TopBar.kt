package com.example.beercompapp.presentation.utils

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.beercompapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerAppTopBar(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    title: String? = null,
    icon: ImageVector?
) {
    val topAppBarState = rememberTopAppBarState()
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.h1,
                color = Color.Black
            )
        },
        navigationIcon = {
            if (icon != null) {
                IconButton(onButtonClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.go_to_authorization_screen)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colors.primary),
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = topAppBarState,
            canScroll = { true }),
        modifier = Modifier.shadow(10.dp)
    )
}
