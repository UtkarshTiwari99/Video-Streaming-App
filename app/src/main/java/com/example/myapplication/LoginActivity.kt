package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.repository.LogInRepository
import com.example.myapplication.repository.Response
import com.example.myapplication.viewModel.LogInViewModel
import com.example.myapplication.viewModel.MainViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class LoginActivity : AppCompatActivity() {

    lateinit var activityLoginBinding: ActivityLoginBinding
    lateinit var logInViewModel:LogInViewModel
    private val sessionManager by lazy {
        SessionManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val logInService= RetrofitInstance.getRetrofitInstance(this).create(ApiInterface::class.java)
        logInViewModel = ViewModelProvider(this,
            MainViewModelFactory(LogInRepository(logInService))
        ).get()
        val toolbar = findViewById<MaterialToolbar>(R.id.appbar)
        setSupportActionBar(toolbar)
        activityLoginBinding=DataBindingUtil.setContentView(this, R.layout.activity_login)
        activityLoginBinding.lifecycleOwner = this
        activityLoginBinding.userData = logInViewModel
        logInViewModel.user.observe(this){
            when(it){
                is Response.Loading -> {}
                is Response.Success -> {
                    activityLoginBinding.userNameLay.error=""
                    activityLoginBinding.passwordLay.error=""
                    Log.d("GokuSon", "onRes: ${it.data}")
                    val token = it.data?.authToken
                            Log.e("GokuSon", token ?: ("ok" + " " +token.toString()))
                            Toast.makeText(baseContext, it.data?.userName, Toast.LENGTH_SHORT).show()
                            if (token != null) {
                                sessionManager.saveAuthToken(token)
                                startActivity(Intent(baseContext, MainActivity::class.java))
                            }
                }
                is Response.Error -> {
                    Log.d("GokuSonError", "onRes: ${it.error}")
                    var errorType= it.error?.split("-")
                    if (errorType != null) {
                        when{
                            errorType[0] == "UserName" -> {
                                activityLoginBinding.passwordLay.error=""
                                activityLoginBinding.userNameLay.error=errorType[1]
                            }
                            errorType[0] == "Password" -> {
                                activityLoginBinding.userNameLay.error=""
                                activityLoginBinding.passwordLay.error=errorType[1]
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activityLoginBinding.login.setOnClickListener { login() }
        activityLoginBinding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun login() {
        logInViewModel.login()
    }
}