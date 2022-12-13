package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.SignUpBody
import com.example.myapplication.models.SignUpResponse
import com.example.myapplication.repository.SignUpRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class SignUpViewModel(private val signUpRepository: SignUpRepository):ViewModel() {
    val userEmail= MutableLiveData<String>()
    val email: LiveData<String>
        get() = userEmail
    val name= MutableLiveData<String>()
    val userName: LiveData<String>
        get() = name

    val userPassword= MutableLiveData<String>()
    val password: LiveData<String>
        get() = userPassword

    private val signUpResponse = MutableLiveData<com.example.myapplication.repository.Response<SignUpResponse>>()
    val user: LiveData<com.example.myapplication.repository.Response<SignUpResponse>>
        get()=signUpResponse

    fun signUp() {
        Log.e("GokuSon", "ninknun")
        val body = userEmail.value?.let {
            name.value?.let { it1 ->
                userPassword.value?.let { it2 ->
                    SignUpBody(
                        it,
                        it1, it2
                    )
                }
            }
        }

        val response = body?.let { signUpRepository.signUp(it) }
        response?.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: retrofit2.Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    signUpResponse.postValue(com.example.myapplication.repository.Response.Success(response.body()))
                } else {
                    Log.i("GokuSon", response.body().toString())
                    val error=Gson().fromJson(response.errorBody()?.charStream(), SignUpResponse::class.java)
                    signUpResponse.postValue(
                        com.example.myapplication.repository.Response.Error((error.message))
                    )
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                signUpResponse.postValue(com.example.myapplication.repository.Response.Error(t.message))
            }
        })
    }
}