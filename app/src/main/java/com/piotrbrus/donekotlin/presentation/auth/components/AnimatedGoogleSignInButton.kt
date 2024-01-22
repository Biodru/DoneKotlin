package com.piotrbrus.donekotlin.presentation.auth.components


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piotrbrus.donekotlin.R.drawable.google_logo

@Composable
fun AnimatedGoogleSignInButton(
    modifier: Modifier = Modifier,
    primaryText: String = "Zaloguj z google",
    icon: Int = google_logo,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    borderStrokeWidth: Dp = 3.dp,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    var loading by remember { mutableStateOf(false) }
    Button(
        onClick = {
            loading = true
            onClick()
        },
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        border = BorderStroke(width = borderStrokeWidth, color = borderColor),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = progressIndicatorColor
            )
        } else {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google logo",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = primaryText,
                color = MaterialTheme.colorScheme.onPrimary,
                style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
            )
        }
    }
}


@Composable
@Preview
fun AnimatedGoogleSignInButtonPreview() {
    AnimatedGoogleSignInButton {}
}