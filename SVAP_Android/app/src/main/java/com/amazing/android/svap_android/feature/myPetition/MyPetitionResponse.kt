package com.amazing.android.svap_android.feature.myPetition

import com.amazing.android.svap_android.type.Type
import java.time.LocalDateTime

data class MyPetitionResponse (
    val id: String,
    val title: String,
    val content: String,
    val dateTime: LocalDateTime,
    val types: Type,
    val location: String,
)