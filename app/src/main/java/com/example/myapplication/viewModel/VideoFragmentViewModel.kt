package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.VideoDetail

class VideoFragmentViewModel:ViewModel(){
    private var actualVideo= MutableLiveData<VideoDetail>()

    val video:LiveData<VideoDetail>
    get() = actualVideo

    fun setVideo(video:VideoDetail){
        actualVideo.postValue(video)
    }
}