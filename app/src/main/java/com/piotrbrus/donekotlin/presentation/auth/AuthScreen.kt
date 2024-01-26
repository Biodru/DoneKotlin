package com.piotrbrus.donekotlin.presentation.auth

import AuthScreenDividerShape
import ThemePreviews
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.piotrbrus.donekotlin.presentation.auth.components.AnimatedGoogleSignInButton

@Composable
fun AuthScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }



    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .zIndex(1f)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(AuthScreenDividerShape())
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .zIndex(2f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .zIndex(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "Helps you get things",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                text = "Done",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            AnimatedGoogleSignInButton(onClick = onSignInClick)
            Spacer(modifier = Modifier.weight(1.0f))

        }
    }
}

@ThemePreviews
@Composable
fun AuthScreenPreview() {
    AuthScreen(state = SignInState()) {}
}