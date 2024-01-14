package com.piotrbrus.donekotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.piotrbrus.donekotlin.util.Constans.WRITE_EDIT_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavigationGraph(startDestination: String, navigationController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navigationController
    ) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(route = Screen.Authentication.route) {

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