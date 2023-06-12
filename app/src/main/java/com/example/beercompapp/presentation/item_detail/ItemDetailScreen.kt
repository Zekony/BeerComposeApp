package com.example.beercompapp.presentation.item_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.beercompapp.R
import com.example.beercompapp.common.Constants
import com.example.beercompapp.data.LocalDataProvider
import com.example.beercompapp.data.entities.CartItem
import com.example.beercompapp.data.entities.ProductItem
import com.example.beercompapp.presentation.core.MenuCategory
import com.example.beercompapp.presentation.ui.theme.BeerCompAppTheme
import com.example.beercompapp.presentation.utils.BeerAppTopBar
import com.example.beercompapp.presentation.utils.CartButtonHelper
import com.example.beercompapp.presentation.utils.LoadingAnimation3
import com.example.beercompapp.presentation.utils.ShoppingCartButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemDetailScreen(
    id: String,
    viewModel: ItemDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getProductById(id)
    }
    val uiState = viewModel.uiState.collectAsState().value

    if (uiState.downloadState == DownloadState.Success) {
        val product = uiState.product.first()!!

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Scaffold(
                topBar = {
                    BeerAppTopBar(
                        onButtonClick = onBackPressed,
                        icon = Icons.Default.ArrowBack
                    )
                },
                bottomBar = {
                    ItemDetailBottomBar(
                        uiState = uiState,
                        item = product,
                        cartHelper = viewModel.shoppingCartButtonHelper
                    )
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    items(
                        items = listOf(product)
                    ) { item ->
                        ItemDetailContent(
                            item = item,
                            isItemLiked = uiState.userLikes.contains(item),
                            isUserActive = uiState.user.login != "",
                            onFabClick = { viewModel.addLike(item) },
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation3()
        }
    }
}


@Composable
fun ItemDetailContent(
    item: ProductItem,
    onFabClick: () -> Unit,
    isItemLiked: Boolean,
    isUserActive: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    start = 10.dp,
                    end = 10.dp,
                    top = 70.dp,
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(Constants.BASE_URL + item.imagePath)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.error_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp)
            )
            FloatingActionButton(
                onClick = onFabClick,
                shape = CircleShape,
                containerColor = if (isUserActive) MaterialTheme.colors.primary else Color.LightGray,
                contentColor = if (isUserActive) MaterialTheme.colors.secondaryVariant else Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-16).dp, 25.dp)
            ) {
                if (isItemLiked) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic__filled_heart),
                        contentDescription = "Like"
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_heart),
                        contentDescription = "Not Like"
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = item.name,
                maxLines = 2,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier.padding(8.dp, top = 10.dp)
            )
            Text(
                text = item.description,
                maxLines = 6,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ItemDetailBottomBar(
    uiState: ItemDetailUiState,
    item: ProductItem,
    cartHelper: CartButtonHelper,
) {
    BottomAppBar(
        elevation = 4.dp,
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = Modifier.background(MaterialTheme.colors.secondaryVariant)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${item.price} ₽",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start

            )
            when (item.category) {
                MenuCategory.Beer ->
                    Text(
                        text = "${item.alcPercentage}%",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End
                    )
                MenuCategory.Snacks ->
                    Text(
                        text = if (item.weight != null) {
                            "${item.weight} гр."
                        } else "",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End
                    )
            }
            ShoppingCartButton(
                productItem = item,
                cartItem = if (uiState.shoppingCart.isEmpty()) {
                    CartItem()
                } else {
                    uiState.shoppingCart.firstOrNull { it.UID == item.UID }
                },
                cartHelper = cartHelper,
            )
        }
    }
}

@Preview
@Composable
private fun ItemDetailContentPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        BeerCompAppTheme {
            ItemDetailContent(
                item = LocalDataProvider.getProductsTestList()[1],
                onFabClick = {},
                isItemLiked = true,
                isUserActive = true
            )
        }
    }
}