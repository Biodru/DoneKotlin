package com.piotrbrus.donekotlin.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.piotrbrus.donekotlin.presentation.auth.AuthScreen
import kotlinx.coroutines.launch


@Composable
fun SetupNavigationGraph(
    startDestination: String,
    navigationController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(
        startDestination = startDestination,
        navController = navigationController
    ) {
        authenticationRoute(googleAuthUiClient)
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(googleAuthUiClient: GoogleAuthUiClient) {
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

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {

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