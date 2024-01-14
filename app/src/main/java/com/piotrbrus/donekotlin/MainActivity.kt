package com.piotrbrus.donekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.piotrbrus.donekotlin.navigation.Screen
import com.piotrbrus.donekotlin.navigation.SetupNavigationGraph
import com.piotrbrus.donekotlin.ui.theme.DoneKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoneKotlinTheme {
                val navController = rememberNavController()
                SetupNavigationGraph(
                    startDestination = Screen.Authentication.route,
                    navigationController = navController
                )
            }
        }
    }
}