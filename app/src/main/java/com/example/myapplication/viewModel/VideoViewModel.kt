package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.VideoDetail
import com.example.myapplication.repository.VideoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModel(private val videoRepository: VideoRepository):ViewModel() {

    private val videos = MutableLiveData<com.example.myapplication.repository.Response<List<VideoDetail>>>()
    val listOfVideos:LiveData<com.example.myapplication.repository.Response<List<VideoDetail>>>
        get()=videos

    fun getAllVideos() {
        val response = videoRepository.getVideos()
        videos.postValue(com.example.myapplication.repository.Response.Loading())
        response.enqueue(object : Callback<List<VideoDetail>> {
            override fun onResponse(call: Call<List<VideoDetail>>, response: Response<List<VideoDetail>>) {
                videos.postValue(com.example.myapplication.repository.Response.Success(response.body()))
            }

            override fun onFailure(call: Call<List<VideoDetail>>, t: Throwable) {
                videos.postValue(com.example.myapplication.repository.Response.Error(t.message))
            }
        })
    }
}