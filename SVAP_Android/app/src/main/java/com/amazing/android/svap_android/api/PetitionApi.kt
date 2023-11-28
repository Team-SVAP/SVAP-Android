package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.main.PopularPetitionResponse
import com.amazing.android.svap_android.feature.showPetition.SortAllResponse
import com.amazing.android.svap_android.feature.showPetition.SortPetitionResponse
import com.amazing.android.svap_android.feature.showPetition.VoteAllResponse
import com.amazing.android.svap_android.feature.showPetition.VoteListResponse
import com.amazing.android.svap_android.type.AccessTypes
import com.amazing.android.svap_android.type.Type
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface PetitionApi {

    @GET("/petition/popular")
    fun popularPetition(
    ): Call<PopularPetitionResponse>

    @GET("/petition/vote/{type}")
    fun voteList(
        @Path("type") type: Type,
    ): Call<List<VoteListResponse>>

    @GET("petition/vote-all")
    fun voteAll(
    ): Call<List<VoteAllResponse>>

    @GET("/petition/sort/{type}/{accessTypes}")
    fun sortPetition(
        @Path("type") type: Type,
        @Path("accessTypes") accessTypes: AccessTypes,
    ): Call<List<SortPetitionResponse>>

    @GET("petition/sort-all/{accessTypes}")
    fun sortPetitionAll(
        @Path("accessTypes") accessTypes: AccessTypes,
    ): Call<List<SortAllResponse>>
}
