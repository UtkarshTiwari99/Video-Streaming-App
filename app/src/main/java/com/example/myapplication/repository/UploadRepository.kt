package com.example.myapplication.repository

import com.example.myapplication.api.ApiInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository(private val videoService:ApiInterface) {
    fun upload(video: MultipartBody.Part,thumbnail: MultipartBody.Part,title: RequestBody,description: RequestBody)=videoService.uploadFile(video,thumbnail,title,description)
}