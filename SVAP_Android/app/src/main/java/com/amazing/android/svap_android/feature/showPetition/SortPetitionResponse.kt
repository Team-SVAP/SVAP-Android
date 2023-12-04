package com.amazing.android.svap_android.feature.showPetition

import com.amazing.android.svap_android.type.Types

data class SortPetitionResponse(
    val id: Long,
    val title: String,
    val dateTime: String,
    val types: Types,
    val location: String,
)
