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

    private var contentId = MutableLiveData<String>()
    private var contentType = MutableLiveData<String>()

    var movie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(contentId) { movieId ->
            appRepository.getMovie(movieId)
        }

    var show: LiveData<Resource<ShowEntity>> = Transformations.switchMap(contentId) { showId ->
        appRepository.getShow(showId)
    }

    var favorite: LiveData<FavoriteEntity> = Transformations.switchMap(contentId) { contentId ->
        Transformations.switchMap(contentType) { contentType ->
            appRepository.getFavorite(contentId, contentType)
        }
    }

    fun saveFavorite() : Boolean {
        contentType.value?.let {
            when(it) {
                MovieEntity.TYPE -> {
                    movie.value?.data?.run {
                        appRepository.saveFavorite(movieId, it)
                    } ?: return false
                }
                ShowEntity.TYPE -> {
                    show.value?.data?.run {
                        appRepository.saveFavorite(showId, it)
                    } ?: return false
                }
            }
            return true
        } ?: return false
    }

    fun removeFavorite() : Boolean {
        contentType.value?.let {
            when(it) {
                MovieEntity.TYPE -> {
                    movie.value?.data?.run {
                        appRepository.removeFavorite(movieId, it)
                    } ?: return false
                }
                ShowEntity.TYPE -> {
                    show.value?.data?.run {
                        appRepository.removeFavorite(showId, it)
                    } ?: return false
                }
            }
            return true
        } ?: return false
    }

    fun setSelectedContentId(contentId: String) {
        this.contentId.value = contentId
    }

    fun setSelectedContentType(contentType: String) {
        this.contentType.value = contentType
    }
}