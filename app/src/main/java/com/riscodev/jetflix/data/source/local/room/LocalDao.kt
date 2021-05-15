package com.riscodev.jetflix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.riscodev.jetflix.data.source.local.entity.FavoriteEntity
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.data.source.local.entity.ShowEntity

@Dao
interface LocalDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM showentities")
    fun getShows(): DataSource.Factory<Int, ShowEntity>

    @Transaction
    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getMovie(movieId: String): LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM showentities WHERE showId = :showId")
    fun getShow(showId: String): LiveData<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertShows(shows: List<ShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShow(show: ShowEntity)

    @Transaction
    @Query("SELECT * FROM movieentities WHERE movieId IN (SELECT contentId FROM favoriteentities WHERE contentType = 'movie')")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM showentities WHERE showId IN (SELECT contentId FROM favoriteentities WHERE contentType = 'show')")
    fun getFavoriteShows(): DataSource.Factory<Int, ShowEntity>

    @Transaction
    @Query("SELECT * FROM favoriteentities WHERE contentId = :contentId AND contentType = :contentType")
    fun getFavorite(contentId: String, contentType: String) : LiveData<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(data: FavoriteEntity)

    @Transaction
    @Query("DELETE FROM favoriteentities WHERE contentId = :contentId AND contentType = :contentType")
    fun deleteFavorite(contentId: String, contentType: String)

}