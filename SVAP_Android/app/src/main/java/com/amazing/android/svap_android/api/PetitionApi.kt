package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.detail.DetailResponse
import com.amazing.android.svap_android.feature.main.PopularPetitionResponse
import com.amazing.android.svap_android.feature.showPetition.SortAllResponse
import com.amazing.android.svap_android.feature.showPetition.SortPetitionResponse
import com.amazing.android.svap_android.feature.showPetition.VoteAllResponse
import com.amazing.android.svap_android.feature.showPetition.VoteListResponse
import com.amazing.android.svap_android.feature.write.WritePetitionRequest
import com.amazing.android.svap_android.type.AccessTypes
import com.amazing.android.svap_android.type.Types
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PetitionApi {

    @POST("/svap/petition/")
    fun postWritePetition(
        @Header("Authorization") accessToken: String,
        @Body request: WritePetitionRequest,
    ): Call<Void>

    @GET("/svap/petition/popular")
    fun popularPetition(
    ): Call<PopularPetitionResponse>

    @GET("/svap/petition/vote/{type}")
    fun voteList(
        @Path("type") types: Types,
    ): Call<List<VoteListResponse>>

    @GET("/svap/petition/vote-all")
    fun voteAll(
    ): Call<List<VoteAllResponse>>

    @GET("/svap/petition/sort/{type}/{accessTypes}")
    fun sortPetition(
        @Path("type") types: Types,
        @Path("accessTypes") accessTypes: AccessTypes,
    ): Call<List<SortPetitionResponse>>

    @GET("/svap/petition/sort-all/{accessTypes}")
    fun sortPetitionAll(
        @Path("accessTypes") accessTypes: AccessTypes,
    ): Call<List<SortAllResponse>>

    @GET("/svap/petition/{petitionId}")
    fun detailShow(
        @Path("petitionId") id: Long,
    ): Call<DetailResponse>
}
