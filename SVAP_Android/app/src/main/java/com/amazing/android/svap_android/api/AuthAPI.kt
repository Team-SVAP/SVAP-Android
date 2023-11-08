package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.login.LoginRequest
import com.amazing.android.svap_android.feature.login.LoginResponse
import com.amazing.android.svap_android.feature.signup.signupId.CkPasswordRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("/login") //로그인
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/ck-password") //회원가입 비번
    fun ckPassword(
        @Body request: CkPasswordRequest
    ): Call<Void>
}