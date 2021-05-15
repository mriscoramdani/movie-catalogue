package com.riscodev.jetflix.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.riscodev.jetflix.data.source.local.entity.FavoriteEntity
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.data.source.local.entity.ShowEntity
import com.riscodev.jetflix.data.source.local.room.LocalDao

class LocalDataSource(private val localDao: LocalDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(localDao: LocalDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(localDao)
    }

    fun getListMovies(): DataSource.Factory<Int, MovieEntity> = localDao.getMovies()

    fun getListShows(): DataSource.Factory<Int, ShowEntity> = localDao.getShows()

    fun getMovie(movieId: String): LiveData<MovieEntity> = localDao.getMovie(movieId)

    fun getShow(showId: String): LiveData<ShowEntity> = localDao.getShow(showId)

    fun insertMovies(data: List<MovieEntity>) = localDao.insertMovies(data)

    fun insertMovie(data: MovieEntity) = localDao.insertMovie(data)

    fun insertShows(data: List<ShowEntity>) = localDao.insertShows(data)

    fun insertShow(data: ShowEntity) = localDao.insertShow(data)

    fun getListFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = localDao.getFavoriteMovies()

    fun getListFavoriteShows(): DataSource.Factory<Int, ShowEntity> = localDao.getFavoriteShows()

    fun getFavorite(id: String, type: String): LiveData<FavoriteEntity> = localDao.getFavorite(id, type)

    fun insertFavorite(favoriteEntity: FavoriteEntity) = localDao.insertFavorite(favoriteEntity)

    fun deleteFavorite(id: String, type: String) = localDao.deleteFavorite(id, type)
}