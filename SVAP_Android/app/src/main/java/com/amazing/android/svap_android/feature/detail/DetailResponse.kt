package com.amazing.android.svap_android.feature.detail

import com.amazing.android.svap_android.type.AccessTypes
import com.amazing.android.svap_android.type.Types

data class DetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val voteCounts: Int,
    val accessTypes: AccessTypes,
    val types: Types,
    val location: String,
    val viewCounts: Int,
    val accountId: String,
    val imgUrl: List<String>,
    val dateTime: String,
    val voted: Boolean,
)
