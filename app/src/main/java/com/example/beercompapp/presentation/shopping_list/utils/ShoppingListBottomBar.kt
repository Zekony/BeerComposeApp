package com.example.beercompapp.presentation.shopping_list.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.beercompapp.R

@Composable
fun ShoppingListBottomBar(sum: Double, onButtonClick: () -> Unit) {
    BottomAppBar(elevation = 4.dp, modifier = Modifier.height(100.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Итого:", style = MaterialTheme.typography.h2)
                Text(text = "$sum ₽", style = MaterialTheme.typography.h2)
            }
            AlertDialogButton(onButtonClick = { onButtonClick() }, enabled = sum != 0.0)
        }
    }
}

@Composable
fun AlertDialogButton(onButtonClick: () -> Unit, enabled: Boolean) {
    val openDialog = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { openDialog.value = true },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            disabledBackgroundColor = Color.LightGray
        ),
        enabled = enabled,
        modifier = Modifier
            .height(35.dp)
            .width(300.dp)
            .clip(RoundedCornerShape(50.dp))
    ) {
        Text(
            text = stringResource(id = R.string.further),
            style = MaterialTheme.typography.h2
        )
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.confirm_order
                    )
                )
            },
            confirmButton = {
                Button(onClick = {
                    onButtonClick()
                    openDialog.value = false
                }) {
                    Text(text = "Да")
                }
            },
            dismissButton = {
                Button(onClick = { openDialog.value = false }) {
                    Text(text = "Нет")
                }
            })
    }
}