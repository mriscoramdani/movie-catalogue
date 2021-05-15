package com.riscodev.jetflix.di

import android.content.Context
import com.riscodev.jetflix.BuildConfig
import com.riscodev.jetflix.data.AppRepository
import com.riscodev.jetflix.data.source.local.LocalDataSource
import com.riscodev.jetflix.data.source.local.room.LocalDatabase
import com.riscodev.jetflix.data.source.remote.RemoteDataSource
import com.riscodev.jetflix.data.source.remote.retrofit.RemoteDao
import com.riscodev.jetflix.data.source.remote.retrofit.RetrofitBuilder
import com.riscodev.jetflix.utils.AppExecutors

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