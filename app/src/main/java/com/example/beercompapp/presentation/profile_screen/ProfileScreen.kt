package com.example.beercompapp.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.beercompapp.R

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    toMainScreen: () -> Unit,
    toAuthorizationScreen: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val isUserActive = state.user.login.isNotBlank()

    fun onLogoutButtonClick() {
        viewModel.logout()
        toMainScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                PaddingValues(
                    start = 17.5.dp,
                    end = 7.5.dp,
                    top = 90.dp,
                    bottom = 20.dp
                )
            )
    ) {
        if (isUserActive) {
            LoggedInScreen(viewModel, state) { onLogoutButtonClick() }
        } else {
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    Text(
                        text = "Привет",
                        style = MaterialTheme.typography.h1,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { toAuthorizationScreen() },
                        modifier = Modifier
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    {
                        Text(
                            text = "Войти",
                            style = MaterialTheme.typography.h2
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun LoggedInScreen(
    viewModel: ProfileScreenViewModel,
    state: ProfileScreenState,
    onLogoutButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.user.login,
                    style = MaterialTheme.typography.h1,
                )
                IconButton(
                    onClick = { onLogoutButtonClick() },
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Изменить логин",
                style = MaterialTheme.typography.body1,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        viewModel.showChangeField(ShowChangeFields.ChangeLogin)
                    }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Изменить пароль",
                style = MaterialTheme.typography.body1,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        viewModel.showChangeField(ShowChangeFields.ChangePassword)
                    }
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {
        Column {
            when (state.showChangeFields) {
                ShowChangeFields.ChangeLogin -> ChangeLogin(state, viewModel)
                ShowChangeFields.ChangePassword -> ChangePassword(state, viewModel)
                ShowChangeFields.None -> {}
            }
            if (state.showChangeFields != ShowChangeFields.None) {
                SaveButton(viewModel, state)
            }
        }
    }
}

@Composable
fun ChangeLogin(state: ProfileScreenState, viewModel: ProfileScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 20.dp, vertical = 15.dp))
    ) {
        OutlinedTextField(
            value = if (state.inputFields.newLogin.length > 8) state.inputFields.newLogin.take(8) else state.inputFields.newLogin,
            onValueChange = viewModel::getLogin,
            label = { Text(stringResource(id = R.string.enter_login)) },
            isError = state.errors.loginError.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ChangePassword(state: ProfileScreenState, viewModel: ProfileScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 20.dp, vertical = 15.dp))
    ) {
        OutlinedTextField(
            value = if (state.inputFields.password.length > 8) state.inputFields.password.take(8) else state.inputFields.password,
            onValueChange = viewModel::getPassword,
            label = { Text(stringResource(id = R.string.enter_password)) },
            isError = state.errors.passwordError.isNotEmpty(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),

            )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = if (state.inputFields.repeatPassword.length > 8) state.inputFields.repeatPassword.take(
                8
            )
            else state.inputFields.repeatPassword,
            onValueChange = viewModel::getRepeatPassword,
            isError = state.errors.passwordError.isNotEmpty(),
            label = { Text(stringResource(id = R.string.enter_password_again)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SaveButton(viewModel: ProfileScreenViewModel, state: ProfileScreenState) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { viewModel.onSaveChanges(context) },
            enabled = state.isButtonActive,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        ) {
            Text(text = stringResource(id = R.string.save_changes))
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(toMainScreen = {}) {}
}