package com.riscodev.jetflix.ui.main.content.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.riscodev.jetflix.data.AppRepository
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.vo.Resource

class MovieViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = appRepository.getListMovies()

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> =
        appRepository.getListFavoriteMovies()
}