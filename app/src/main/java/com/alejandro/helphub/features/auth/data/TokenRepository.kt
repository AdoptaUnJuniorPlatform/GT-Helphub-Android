package com.alejandro.helphub.features.auth.data

import android.content.SharedPreferences
import javax.inject.Inject

class TokenRepository(private val sharedPreferences: SharedPreferences){
    private val tokenKey="token_key"
    fun getToken():String?{
        return sharedPreferences.getString(tokenKey,null)
    }
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(tokenKey, token).apply()
    }
}