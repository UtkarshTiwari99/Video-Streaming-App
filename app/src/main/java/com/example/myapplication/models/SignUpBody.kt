package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class SignUpBody(

    @SerializedName("email")
    val email:String,

    @SerializedName("username")
    val username:String,

    @SerializedName("password")
    val password:String)