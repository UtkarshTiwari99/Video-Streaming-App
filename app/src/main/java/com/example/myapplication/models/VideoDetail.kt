package com.example.myapplication.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class VideoDetail(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description:String,
    @SerializedName("url")
    val url:String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerializedName("views")
    val views:Int,
    @SerializedName("channelName")
    val channelName:String)