package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.AuthResponse
import com.example.myapplication.models.LoginBody
import com.example.myapplication.repository.LogInRepository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInViewModel(private val logInRepository: LogInRepository):ViewModel() {
    val name=MutableLiveData<String>()
    val userName:LiveData<String>
    get() = name

    val userPassword=MutableLiveData<String>()
    val password:LiveData<String>
        get() = userPassword

    private val logInResponse = MutableLiveData<com.example.myapplication.repository.Response<AuthResponse>>()
    val user: LiveData<com.example.myapplication.repository.Response<AuthResponse>>
        get()=logInResponse

    fun login() {
        val response = userName.value?.let { password.value?.let { it1 -> LoginBody(it, it1) } }
            ?.let { logInRepository.login(it) }
            response?.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        logInResponse.postValue(
                            com.example.myapplication.repository.Response.Success(
                                response.body()
                            )
                        )
                    } else {
                        var error = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            AuthResponse::class.java
                        )
                        if (error.authToken.isEmpty())
                            logInResponse.postValue(
                                com.example.myapplication.repository.Response.Error(
                                    "UserName-" + error.userName
                                )
                            )
                        else
                            logInResponse.postValue(
                                com.example.myapplication.repository.Response.Error(
                                    "Password-" + error.authToken
                                )
                            )
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    logInResponse.postValue(com.example.myapplication.repository.Response.Error(t.message))
                }
            })
        }

}