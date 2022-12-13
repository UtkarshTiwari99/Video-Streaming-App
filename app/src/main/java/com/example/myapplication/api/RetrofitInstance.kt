package com.example.myapplication.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val BASEURL: String = Constants.BASEURL

        private fun okhttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
        }

        fun getRetrofitInstance(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}