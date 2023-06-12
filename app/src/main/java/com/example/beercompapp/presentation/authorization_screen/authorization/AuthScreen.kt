package com.example.beercompapp.presentation.authorization_screen.authorization

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beercompapp.R
import com.example.beercompapp.presentation.authorization_screen.authorization.utils.LoginFieldsErrors
import com.example.beercompapp.presentation.utils.BeerAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    toMainMenu: () -> Unit,
    toRegistrationScreen: () -> Unit
) {
    val event = viewModel.onSuccessEvent.collectAsState(false).value
    LaunchedEffect(event) {
        if (event) {
            toMainMenu()
        }
    }
    val error = viewModel.userState.collectAsState().value.errors

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                BeerAppTopBar(
                    onButtonClick = onBackPressed,
                    icon = Icons.Default.ArrowBack
                )
            },
            bottomBar = {}
        ) {
            AuthScreenContent(
                viewModel = viewModel,
                errors = error,
                toRegistrationScreen = toRegistrationScreen,
            )
        }
    }
}

@Composable
fun AuthScreenContent(
    viewModel: AuthScreenViewModel,
    errors: LoginFieldsErrors,
    toRegistrationScreen: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.userState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
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
            value = state.loginInputFields.number,
            onValueChange = viewModel::getNumber,
            label = { Text(stringResource(id = R.string.enter_number)) },
            singleLine = true,
            isError = errors.numberError.isNotEmpty(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.loginInputFields.password.joinToString(""),
            onValueChange = viewModel::getPassword,
            label = { Text(stringResource(id = R.string.enter_password)) },
            singleLine = true,
            isError = errors.passwordError.isNotEmpty(),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.authorize(context) },
            enabled = state.isButtonActive,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.enter))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.no_account))
        Text(
            text = stringResource(id = R.string.to_registration_screen),
            color = Color.Blue,
            modifier = Modifier.clickable { toRegistrationScreen() })
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    //AuthScreenContent()
}

