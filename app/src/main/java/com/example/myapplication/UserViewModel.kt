package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.User

class UserViewModel:ViewModel() {
    private val userData:MutableLiveData<User> by lazy{
        MutableLiveData<User>()
    }
    val mUserData:LiveData<User>
    get() = userData

    fun setUser(data : User){
        userData.value=data
    }
}