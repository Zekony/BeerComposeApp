package com.example.beercompapp.presentation.authorization_screen.registration

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beercompapp.R
import com.example.beercompapp.presentation.utils.BeerAppTopBar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel = hiltViewModel(),
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
            AuthScreenContentRegistration(viewModel = viewModel)
        }
    }
}


@Composable
fun AuthScreenContentRegistration(viewModel: RegistrationScreenViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
            value = "",
            onValueChange = viewModel::getNumber,
            label = { stringResource(id = R.string.enter_number) },
            singleLine = true,
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = viewModel::getLogin,
            label = { stringResource(id = R.string.enter_login) },
            singleLine = true,
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = viewModel::getPassword,
            label = { stringResource(id = R.string.enter_password) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.width(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = viewModel::getRepeatPassword,
            label = { stringResource(id = R.string.enter_password_again) },
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
    }
}

