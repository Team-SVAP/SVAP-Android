package com.amazing.android.svap_android.feature.showPetition

import com.amazing.android.svap_android.type.Type

data class VoteAllResponse(
    val id: Long,
    val title: String,
    val dateTime: String,
    val types: Type,
    val location: String,
)
