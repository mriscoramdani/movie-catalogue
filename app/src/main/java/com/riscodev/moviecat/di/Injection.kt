package com.riscodev.moviecat.di

import android.content.Context
import com.riscodev.moviecat.BuildConfig
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.LocalDataSource
import com.riscodev.moviecat.data.source.local.room.LocalDatabase
import com.riscodev.moviecat.data.source.remote.RemoteDataSource
import com.riscodev.moviecat.data.source.remote.retrofit.RemoteDao
import com.riscodev.moviecat.data.source.remote.retrofit.RetrofitBuilder
import com.riscodev.moviecat.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val database = LocalDatabase.getInstance(context)
        val retrofit = RetrofitBuilder.build(RemoteDao::class.java)
        val apiRequestKey = BuildConfig.API_KEY
        val appExecutors = AppExecutors()

        val remoteDataSource = RemoteDataSource.getInstance(retrofit, apiRequestKey)
        val localDataSource = LocalDataSource.getInstance(database.localDao())
        return AppRepository.getInstance(
            remoteDataSource,
            localDataSource,
            appExecutors
        )
    }
}