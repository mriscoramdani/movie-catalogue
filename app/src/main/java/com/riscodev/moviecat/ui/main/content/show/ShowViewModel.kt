package com.riscodev.moviecat.ui.main.content.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.vo.Resource

class ShowViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getShows() : LiveData<Resource<PagedList<ShowEntity>>> = appRepository.getListShows()

    fun getFavoriteShows() : LiveData<PagedList<ShowEntity>> = appRepository.getListFavoriteShows()
}