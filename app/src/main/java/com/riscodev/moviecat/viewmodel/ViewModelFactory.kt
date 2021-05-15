package com.riscodev.moviecat.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.di.Injection
import com.riscodev.moviecat.ui.detail.DetailViewModel
import com.riscodev.moviecat.ui.main.content.movie.MovieViewModel
import com.riscodev.moviecat.ui.main.content.show.ShowViewModel

class ViewModelFactory private constructor(private val appRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(ShowViewModel::class.java) -> {
                return ShowViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(appRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}