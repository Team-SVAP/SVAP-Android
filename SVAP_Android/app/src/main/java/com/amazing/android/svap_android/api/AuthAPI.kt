package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.login.LoginRequest
import com.amazing.android.svap_android.feature.login.LoginResponse
import com.amazing.android.svap_android.feature.signup.signupId.SignUpIdRequest
import com.amazing.android.svap_android.feature.signup.signupPw.CkPasswordRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("/login") //로그인
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/user/ck-username") //회원가입 이름
    fun ckId(
        @Body request: SignUpIdRequest
    ): Call<Void>

    @POST("/ck-password") //회원가입 비번
    fun ckPassword(
        @Body request: CkPasswordRequest
    ): Call<Void>
}