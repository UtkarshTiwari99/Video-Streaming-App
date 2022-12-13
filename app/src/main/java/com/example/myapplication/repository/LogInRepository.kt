package com.example.myapplication.repository

import com.example.myapplication.api.ApiInterface
import com.example.myapplication.models.LoginBody

class LogInRepository(private val loginService: ApiInterface) {
     fun login(loginBody: LoginBody)= loginService.login(loginBody)
}