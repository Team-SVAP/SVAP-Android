package com.amazing.android.svap_android.feature.home

data class PopularPetitionResponse(
    val id: Long,
    val title: String,
    val content: String,
    val dateTime: String,
    val types: String,
    val location: String,
)
