package com.amazing.android.svap_android.feature.main

data class PopularPetitionResponse(
    val id: Int,
    val title: String,
    val content: String,
    val dateTime: String,
    val types: String,
    val location: String,
)
