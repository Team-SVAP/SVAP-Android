package com.amazing.android.svap_android.feature.signup

import com.google.gson.annotations.SerializedName

data class SignUpResponse (
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
)