package com.alejandro.helphub.core.di

import com.alejandro.helphub.features.auth.data.network.clients.TwofaClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
       return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4002/api/helphub/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideTwofaClient(retrofit: Retrofit):TwofaClient{
       return  retrofit.create(TwofaClient::class.java)
    }
}