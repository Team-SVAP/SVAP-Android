package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.login.LoginRequest
import com.amazing.android.svap_android.feature.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}