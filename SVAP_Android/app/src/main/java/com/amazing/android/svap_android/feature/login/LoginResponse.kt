package com.amazing.android.svap_android.feature.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
