package com.riscodev.moviecat.ui.main.content.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.vo.Resource

class MovieViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = appRepository.getListMovies()

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> =
        appRepository.getListFavoriteMovies()
}