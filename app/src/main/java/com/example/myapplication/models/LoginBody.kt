package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class LoginBody(

    @SerializedName("username")
    var userName: String,

    @SerializedName("password")
    var password: String
    )