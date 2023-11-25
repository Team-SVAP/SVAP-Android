package com.amazing.android.svap_android.feature.myPetition

import com.amazing.android.svap_android.type.Type

data class MyPetitionResponse(
    val id: String,
    val title: String,
    val content: String,
    val dateTime: String,
    val types: Type,
    val location: String,
)