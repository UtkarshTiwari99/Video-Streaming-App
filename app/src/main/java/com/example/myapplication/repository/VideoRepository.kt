package com.example.myapplication.repository

import com.example.myapplication.api.ApiInterface

class VideoRepository(private val videoService: ApiInterface) {
   fun getVideos()= videoService.getAllVideo()
}