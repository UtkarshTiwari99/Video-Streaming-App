package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.repository.SignUpRepository
import com.example.myapplication.viewModel.MainViewModelFactory
import com.example.myapplication.viewModel.SignUpViewModel
import com.google.android.material.appbar.MaterialToolbar

class SignUpActivity : AppCompatActivity(){

    private lateinit var activitySignupBinding: ActivitySignupBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private val sessionManager by lazy {
        SessionManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            val signUpService =
                RetrofitInstance.getRetrofitInstance(this).create(ApiInterface::class.java)
            signUpViewModel = ViewModelProvider(
                this,
                MainViewModelFactory(SignUpRepository(signUpService))
            ).get()
        val toolbar = findViewById<MaterialToolbar>(R.id.appbar)
        setSupportActionBar(toolbar)
            activitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
            activitySignupBinding.lifecycleOwner = this
            activitySignupBinding.userData = signUpViewModel
    }

    override fun onStart() {
        super.onStart()
        signUpViewModel.user.observe(this){
            when(it){
                is com.example.myapplication.repository.Response.Loading -> {}
                is com.example.myapplication.repository.Response.Success -> {
                    Log.d("GokuSon", "onRes: ${it.data}")
                    val token = it.data?.toString()
                    if (token != null) {
                        startActivity(Intent(baseContext, LoginActivity::class.java))
                    }
                }
                is com.example.myapplication.repository.Response.Error -> {
                    activitySignupBinding.usernameLay.error=it.error
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activitySignupBinding.signUp.setOnClickListener { sigUp() }
    }

    private fun sigUp(){
        signUpViewModel.signUp()
    }

}