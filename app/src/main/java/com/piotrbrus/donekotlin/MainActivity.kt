package com.piotrbrus.donekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.piotrbrus.donekotlin.navigation.Screen
import com.piotrbrus.donekotlin.navigation.SetupNavigationGraph
import com.piotrbrus.donekotlin.presentation.auth.GoogleAuthUiClient
import com.piotrbrus.donekotlin.ui.theme.DoneKotlinTheme

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoneKotlinTheme {
                val navController = rememberNavController()
                SetupNavigationGraph(
                    startDestination = Screen.Authentication.route,
                    navigationController = navController,
                    googleAuthUiClient = googleAuthUiClient,
                    applicationContext = applicationContext
                )
            }
        }
    }
}