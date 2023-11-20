package com.amazing.android.svap_android.api

import com.amazing.android.svap_android.feature.main.PopularPetitionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface PetitionApi {

    @GET("/petition/popular")
    fun popularPetition(
    ): Call<PopularPetitionResponse>
}
