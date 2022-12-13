package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class VideoUploadRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description:String)