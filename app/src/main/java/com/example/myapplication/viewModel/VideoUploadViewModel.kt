package com.example.myapplication.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.APIResponse
import com.example.myapplication.models.SignUpResponse
import com.example.myapplication.repository.Response
import com.example.myapplication.repository.UploadRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class VideoUploadViewModel(private val uploadRepository: UploadRepository):ViewModel() {

    var video= MutableLiveData<Uri>()
    var thumbnail=MutableLiveData<Uri>()
    var title=MutableLiveData<String>()
    var description=MutableLiveData<String>()

    val response=MutableLiveData<Response<String>>()

    fun upload(video: MultipartBody.Part, thumbnail: MultipartBody.Part, title: RequestBody, description: RequestBody){
        response.postValue(Response.Loading())
        uploadRepository.upload(video,thumbnail,title,description).enqueue(object :
            retrofit2.Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, res: retrofit2.Response<APIResponse>) {
                if(res.isSuccessful){
                    response.postValue(Response.Success(res.body()!!.message))
                }else{
                    val error=Gson().fromJson(res.errorBody()?.charStream(), SignUpResponse::class.java)
                    response.postValue(Response.Error(error.message))
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                response.postValue(Response.Error("Network Error ${t.message}"))
            }
        })
    }

}