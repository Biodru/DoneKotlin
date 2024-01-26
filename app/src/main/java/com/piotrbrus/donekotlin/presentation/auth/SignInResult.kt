package com.piotrbrus.donekotlin.presentation.auth

import android.security.identity.AccessControlProfileId

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
)