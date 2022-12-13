package com.example.myapplication.api

import com.example.myapplication.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("/authenticate")
    fun login(@Body info: LoginBody): Call<AuthResponse>

    @Headers("Content-Type:application/json")
    @POST("/signup")
    fun signup(@Body info: SignUpBody): Call<SignUpResponse>

    @GET("/videos")
    fun getAllVideo():Call<List<VideoDetail>>

    @Multipart
    @POST("/upload")
    fun uploadFile(@Part video:MultipartBody.Part,@Part thumbnail:MultipartBody.Part,@Part("title") title:RequestBody,@Part("description") description:RequestBody):Call<APIResponse>
}