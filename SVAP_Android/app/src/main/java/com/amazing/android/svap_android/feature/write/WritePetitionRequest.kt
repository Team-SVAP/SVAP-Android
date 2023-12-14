package com.amazing.android.svap_android.feature.write

import com.amazing.android.svap_android.type.Types

data class WritePetitionRequest (
    val title: String,
    val content: String,
    val types: Types,
    val location: String,
    val imageUrl: List<String>,
)
