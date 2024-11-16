package com.alejandro.helphub.data.source.remote.repository

import android.content.SharedPreferences

class TokenRepository(private val sharedPreferences: SharedPreferences){
    private val tokenKey="token_key"
    fun getToken():String?{
        return sharedPreferences.getString(tokenKey,null)
    }
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(tokenKey, token).apply()
    }
}