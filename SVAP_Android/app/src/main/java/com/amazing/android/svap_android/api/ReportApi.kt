package com.amazing.android.svap_android.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportApi {

    @POST("/svap/report/{petitionId}")
    fun report(
        @Path("petitionId") id: Long,
    ): Call<Void>
}