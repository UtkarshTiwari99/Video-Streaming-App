package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log

class SplashScreen: AppCompatActivity() {

    private val sessionManager by lazy {
        SessionManager(baseContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
    }

    override fun onResume() {
        super.onResume()
        try {
            if (sessionManager.isExist())
                homeActivity()
            else
                loginActivity()
        }
        catch (e:Exception){
            Log.e("GokuSon",e.message.toString())
        }
    }

    private fun homeActivity(){
        val intent= Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loginActivity(){
        val intent= Intent(baseContext, LoginActivity::class.java)
        startActivity(intent)
    }
}