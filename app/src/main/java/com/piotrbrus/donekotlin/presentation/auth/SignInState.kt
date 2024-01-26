package com.piotrbrus.donekotlin.presentation.auth

data class SignInState(
    val isSigninSuccessful: Boolean = false,
    val signInError: String? = null
)