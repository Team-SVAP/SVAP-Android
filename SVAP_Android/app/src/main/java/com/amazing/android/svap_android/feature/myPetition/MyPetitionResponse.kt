package com.amazing.android.svap_android.feature.myPetition

import com.amazing.android.svap_android.type.Types

data class MyPetitionResponse(
    val id: String,
    val title: String,
    val content: String,
    val dateTime: String,
    val types: Types,
    val location: String,
)