package com.riscodev.moviecat.data.source.remote.retrofit

import com.riscodev.moviecat.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = BuildConfig.API_URL

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }
}