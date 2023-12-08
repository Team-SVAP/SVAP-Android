package com.amazing.android.svap_android.feature.main

import com.amazing.android.svap_android.type.Role

data class UserInfoResponse(
    val userName: String,
    val role: Role,
    val accountId: String,
)