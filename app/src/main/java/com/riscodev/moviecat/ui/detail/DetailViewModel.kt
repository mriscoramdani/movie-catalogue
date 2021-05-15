package com.riscodev.moviecat.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.FavoriteEntity
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.vo.Resource

class DetailViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var movieId = MutableLiveData<String>()
    private var showId = MutableLiveData<String>()

    fun getMovie(): LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieId) { movieId ->
            appRepository.getMovie(movieId)
        }

    fun getShow(): LiveData<Resource<ShowEntity>> = Transformations.switchMap(showId) { showId ->
        appRepository.getShow(showId)
    }

    fun getFavorite(contentId: String, contentType: String): LiveData<FavoriteEntity> =
        appRepository.getFavorite(contentId, contentType)

    fun saveFavorite(contentId: String, contentType: String) =
        appRepository.saveFavorite(contentId, contentType)

    fun removeFavorite(contentId: String, contentType: String) =
        appRepository.removeFavorite(contentId, contentType)

    fun getSelectedMovieId() : String? {
        return movieId.value
    }

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    fun getSelectedShowId() : String? {
        return showId.value
    }

    fun setSelectedShow(showId: String) {
        this.showId.value = showId
    }
}