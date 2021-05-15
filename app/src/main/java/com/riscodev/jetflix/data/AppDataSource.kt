package com.riscodev.jetflix.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.riscodev.jetflix.data.source.local.entity.FavoriteEntity
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.data.source.local.entity.ShowEntity
import com.riscodev.jetflix.vo.Resource

interface AppDataSource {

    fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getListShows(): LiveData<Resource<PagedList<ShowEntity>>>

    fun getMovie(id: String): LiveData<Resource<MovieEntity>>

    fun getShow(id: String): LiveData<Resource<ShowEntity>>

    fun getListFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getListFavoriteShows(): LiveData<PagedList<ShowEntity>>

    fun getFavorite(contentId: String, contentType: String) : LiveData<FavoriteEntity>

    fun saveFavorite(contentId: String, contentType: String)

    fun removeFavorite(contentId: String, contentType: String)
}