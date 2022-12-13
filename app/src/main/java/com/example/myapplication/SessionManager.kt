package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    private var preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun isExist():Boolean{
        return preferences.contains(USER_TOKEN)
    }

    fun fetchAuthToken(): String? {
        if(isExist())
        return preferences.getString(USER_TOKEN, null)
        return ""
    }

    fun logout(){
        if(preferences.contains(USER_TOKEN))
         preferences.edit {
            remove(USER_TOKEN)
            commit()
        }
    }
}











