package com.example.myapplication.repository

import com.example.myapplication.api.ApiInterface
import com.example.myapplication.models.SignUpBody

class SignUpRepository(private val signUpService: ApiInterface) {
    fun signUp(signUpBody: SignUpBody)=signUpService.signup(signUpBody)
}