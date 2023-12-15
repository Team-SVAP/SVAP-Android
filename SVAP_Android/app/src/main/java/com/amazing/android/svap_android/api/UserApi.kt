package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.login.LoginRequest
import com.amazing.android.svap_android.feature.login.LoginResponse
import com.amazing.android.svap_android.feature.myPage.UserInfoResponse
import com.amazing.android.svap_android.feature.myPetition.MyPetitionResponse
import com.amazing.android.svap_android.feature.signup.SignUpRequest
import com.amazing.android.svap_android.feature.signup.SignUpResponse
import com.amazing.android.svap_android.feature.signup.signupId.SignUpIdRequest
import com.amazing.android.svap_android.feature.signup.signupName.SignUpNameRequest
import com.amazing.android.svap_android.feature.signup.signupPw.SignUpPwRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("/svap/user/login") //로그인
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/svap/user/ck-account-id") //회원가입 아이디
    fun ckId(
        @Body request: SignUpIdRequest
    ): Call<Void>

    @POST("/svap/user/ck-password") //회원가입 비번
    fun ckPassword(
        @Body request: SignUpPwRequest
    ): Call<Void>

    @POST("/svap/user/ck-username") //회원가입 이름
    fun ckName(
        @Body request: SignUpNameRequest
    ): Call<Void>

    @POST("/svap/user/signup") //회원가입
    fun signup(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @GET("/svap/user/") //내가 쓴 청원 보기
    fun showMyPetition(
        @Header("Authorization") accessToken: String
    ): Call<List<MyPetitionResponse>>

    @GET("/svap/user/my-info")
    fun myInfo(
        @Header("Authorization") accessToken: String
    ): Call<UserInfoResponse>

    @DELETE("/svap/user")
    fun deleteUser(
        @Header("Authorization") accessToken: String
    ): Call<Void>
}