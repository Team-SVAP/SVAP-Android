package com.amazing.android.svap_android.feature.showPetition

import com.amazing.android.svap_android.type.AccessTypes

data class AccessTypesModel(
    val name: String,
    val accessTypes: AccessTypes,
    var isClicked: Boolean = false,
)
