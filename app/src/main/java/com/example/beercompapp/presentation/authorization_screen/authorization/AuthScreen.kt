package com.example.beercompapp.presentation.authorization_screen.authorization

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beercompapp.R
import com.example.beercompapp.presentation.utils.BeerAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Scaffold(
            topBar = {
                BeerAppTopBar(
                    onButtonClick = {},
                    icon = Icons.Default.ArrowBack
                )
            },
            bottomBar = {}
        ) {
            AuthScreenContent(viewModel = viewModel)

        }
    }
}

@Composable
fun AuthScreenContent(viewModel: AuthScreenViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.authorization),
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = viewModel::getLogin,
            label = { Text("Login") },
            singleLine = true,
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = viewModel::getPassword,
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.enter_login))
        }
        Text(text = stringResource(id = R.string.no_account))
        Text(
            text = stringResource(id = R.string.to_registration_screen),
            modifier = Modifier.clickable { })
    }
}


@Preview
@Composable
fun AuthScreenPreview() {
    //AuthScreenContent()
}

