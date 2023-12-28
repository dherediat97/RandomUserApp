package com.dherediat97.randomuserapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtil {
    private const val BASE_URL = "https://randomuser.me/api/"

    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val randomUserService: RandomUserService by lazy {
        retrofit.create(RandomUserService::class.java)
    }
}