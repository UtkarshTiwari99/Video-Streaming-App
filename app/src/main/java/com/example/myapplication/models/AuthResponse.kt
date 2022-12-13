package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("authToken")
    val authToken: String)