package com.piotrbrus.donekotlin.navigation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.piotrbrus.donekotlin.presentation.auth.GoogleAuthUiClient
import com.piotrbrus.donekotlin.presentation.auth.SignInViewModel
import com.piotrbrus.donekotlin.util.Constans.WRITE_EDIT_SCREEN_ARGUMENT_KEY
import com.piotrbrus.donekotlin.presentation.HomeScreen
import com.piotrbrus.donekotlin.presentation.auth.AuthScreen
import kotlinx.coroutines.launch


@Composable
fun SetupNavigationGraph(
    startDestination: String,
    navigationController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context
) {
    NavHost(
        startDestination = startDestination,
        navController = navigationController
    ) {
        authenticationRoute(
            googleAuthUiClient = googleAuthUiClient,
            navigationController = navigationController
        )
        homeRoute(
            navigationController = navigationController,
            googleAuthUiClient = googleAuthUiClient,
            applicationContext = applicationContext
        )
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    googleAuthUiClient: GoogleAuthUiClient, navigationController: NavHostController
) {
    composable(route = Screen.Authentication.route) {
        val viewModel = viewModel<SignInViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.getSignInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        viewModel.onSignInResult(result = signInResult)
                    }
                }
            }
        )

        LaunchedEffect(key1 = Unit, block = {
            if (googleAuthUiClient.getSignedInUser() != null) {
                navigationController.navigate(Screen.Home.route)
            }
        })

        LaunchedEffect(key1 = state.isSigninSuccessful, block = {
            if (state.isSigninSuccessful) {
                navigationController.navigate(Screen.Home.route)
            }
        })

        AuthScreen(state = state, onSignInClick = {
            scope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        })
    }
}

fun NavGraphBuilder.homeRoute(
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    navigationController: NavHostController
) {
    composable(route = Screen.Home.route) {
        val scope = rememberCoroutineScope()
        val viewModel = viewModel<SignInViewModel>()
        HomeScreen(userData = googleAuthUiClient.getSignedInUser(), onSignOut = {
            scope.launch {
                googleAuthUiClient.signOut()
                Toast.makeText(
                    applicationContext,
                    "Wyloguj",
                    Toast.LENGTH_LONG
                ).show()

                navigationController.popBackStack()
                viewModel.resetState()
            }
        })
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.WriteEdit.route,
        arguments = listOf(navArgument(name = WRITE_EDIT_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}