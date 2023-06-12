package com.example.beercompapp.presentation.authorization_screen.registration

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beercompapp.R
import com.example.beercompapp.presentation.authorization_screen.registration.utils.RegistrationFieldsErrors
import com.example.beercompapp.presentation.utils.BeerAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    toAuthorizationScreen: () -> Unit
) {
    val event = viewModel.onSuccessEvent.collectAsState(false).value
    val errors = viewModel.userState.collectAsState().value.errors
    LaunchedEffect(event) {
        if (event) {
            toAuthorizationScreen()
        }
    }

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
            bottomBar = {}
        ) {
            val context = LocalContext.current
            RegistrationScreenContent(
                viewModel = viewModel,
                errors = errors,
                toAuthorizationScreen = toAuthorizationScreen,
                createUser = { viewModel.createUser(context) }
            )
        }
    }
}


@Composable
fun RegistrationScreenContent(
    viewModel: RegistrationScreenViewModel,
    toAuthorizationScreen: () -> Unit,
    errors: RegistrationFieldsErrors,
    createUser: () -> Unit
) {
    val state = viewModel.userState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.registration),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.registrationInputFields.number,
            onValueChange = viewModel::getNumber,
            label = { Text(text = stringResource(id = R.string.enter_number)) },
            isError = errors.numberError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.registrationInputFields.login,
            onValueChange = viewModel::getLogin,
            label = { Text(text = stringResource(id = R.string.enter_login)) },
            isError = errors.loginError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.registrationInputFields.password.joinToString(""),
            onValueChange = viewModel::getPassword,
            label = { Text(text = stringResource(id = R.string.enter_password)) },
            singleLine = true,
            isError = errors.passwordError.isNotEmpty(),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.registrationInputFields.repeatPassword.joinToString(""),
            onValueChange = viewModel::getRepeatPassword,
            label = { Text(text = stringResource(id = R.string.enter_password_again)) },
            singleLine = true,
            isError = errors.repeatPasswordError.isNotEmpty(),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = createUser,
            enabled = state.isButtonActive,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.create_account))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.to_auth_screen),
            color = androidx.compose.ui.graphics.Color.Blue,
            modifier = Modifier.clickable { toAuthorizationScreen() })
    }
}

