package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("message")
    val message: String)