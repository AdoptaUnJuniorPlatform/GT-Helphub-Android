package com.alejandro.helphub.di

import android.content.Context
import android.content.SharedPreferences
import com.alejandro.helphub.data.source.remote.repository.TokenRepository
import com.alejandro.helphub.data.source.remote.server.AuthClient
import com.alejandro.helphub.data.source.remote.server.ProfileClient
import com.alejandro.helphub.data.source.remote.server.SkillClient
import com.alejandro.helphub.data.source.remote.server.TwofaClient

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideTokenRepository(sharedPreferences: SharedPreferences): TokenRepository {
        return TokenRepository(sharedPreferences)
    }
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
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
    fun provideTwofaClient(retrofit: Retrofit): TwofaClient {
       return  retrofit.create(TwofaClient::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthClient(retrofit: Retrofit): AuthClient {
        return  retrofit.create(AuthClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileClient(retrofit:Retrofit): ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }

       @Singleton
    @Provides
    fun provideSkillClient(retrofit: Retrofit): SkillClient {
        return retrofit.create(SkillClient::class.java)
    }


}