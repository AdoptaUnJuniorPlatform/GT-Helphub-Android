package com.alejandro.helphub.core.di

import com.alejandro.helphub.features.auth.data.network.clients.InfoClient
import com.alejandro.helphub.features.auth.data.network.clients.TwofaClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
       return Retrofit.Builder()
           .baseUrl("http://10.0.2.2:4002/api/helphub/")
           .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideTwofaClient(retrofit: Retrofit):TwofaClient{
       return  retrofit.create(TwofaClient::class.java)
    }
    @Singleton
    @Provides
    fun provideInfoClient(retrofit:Retrofit):InfoClient{
        return retrofit.create(InfoClient::class.java)
    }
}