package com.example.beercompapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.beercompapp.presentation.core.BeerAppNavController
import com.example.beercompapp.presentation.ui.theme.BeerCompAppTheme
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCenter.start(
            application, "4e587244-6678-4c2b-bd66-52bd769dd238",
            Analytics::class.java, Crashes::class.java
        )
        super.onCreate(savedInstanceState)
        setContent {
            BeerCompAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    BeerAppNavController()
                }
            }
        }
    }
}