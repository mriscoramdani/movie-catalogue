package com.riscodev.jetflix.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.riscodev.jetflix.BuildConfig
import com.riscodev.jetflix.data.source.local.LocalDataSource
import com.riscodev.jetflix.data.source.local.entity.FavoriteEntity
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.data.source.local.entity.ShowEntity
import com.riscodev.jetflix.data.source.remote.ApiResponse
import com.riscodev.jetflix.data.source.remote.RemoteDataSource
import com.riscodev.jetflix.data.source.remote.response.MovieResponse
import com.riscodev.jetflix.data.source.remote.response.ShowResponse
import com.riscodev.jetflix.utils.AppExecutors
import com.riscodev.jetflix.vo.Resource

class AppRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : AppDataSource {

    private var config: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(4)
        .setPageSize(4)
        .build()

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> =
                LivePagedListBuilder(localDataSource.getListMovies(), config).build()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getListMovie()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieEntities = ArrayList<MovieEntity>()
                data.forEach {
                    val movie = MovieEntity(
                        it.id.toString(),
                        BuildConfig.API_IMAGE_URL + it.posterPath,
                        it.overview,
                        it.releaseDate,
                        it.title,
                        it.voteAverage
                    )
                    movieEntities.add(movie)
                }
                localDataSource.insertMovies(movieEntities)
            }
        }.asLiveData()
    }

    override fun getListShows(): LiveData<Resource<PagedList<ShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ShowEntity>, List<ShowResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<ShowEntity>> =
                LivePagedListBuilder(localDataSource.getListShows(), config).build()

            public override fun createCall(): LiveData<ApiResponse<List<ShowResponse>>> =
                remoteDataSource.getListShow()

            public override fun saveCallResult(data: List<ShowResponse>) {
                val showEntities = ArrayList<ShowEntity>()
                data.forEach {
                    val show = ShowEntity(
                        it.id.toString(),
                        BuildConfig.API_IMAGE_URL + it.posterPath,
                        it.overview,
                        it.firstAirDate,
                        it.originalName,
                        it.voteAverage
                    )
                    showEntities.add(show)
                }
                localDataSource.insertShows(showEntities)
            }
        }.asLiveData()
    }

    override fun getMovie(id: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovie(id)

            public override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovie(id)

            public override fun saveCallResult(data: MovieResponse) = Unit
        }.asLiveData()
    }

    override fun getShow(id: String): LiveData<Resource<ShowEntity>> {
        return object : NetworkBoundResource<ShowEntity, ShowResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<ShowEntity> =
                localDataSource.getShow(id)

            public override fun createCall(): LiveData<ApiResponse<ShowResponse>> =
                remoteDataSource.getShow(id)

            public override fun saveCallResult(data: ShowResponse) = Unit
        }.asLiveData()
    }

    override fun getListFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getListFavoriteMovies(), config).build()
    }

    override fun getListFavoriteShows(): LiveData<PagedList<ShowEntity>> {
        return LivePagedListBuilder(localDataSource.getListFavoriteShows(), config).build()
    }

    override fun getFavorite(contentId: String, contentType: String): LiveData<FavoriteEntity> {
        return localDataSource.getFavorite(contentId, contentType)
    }

    override fun saveFavorite(contentId: String, contentType: String) {
        appExecutors.diskIO().execute {
            localDataSource.insertFavorite(FavoriteEntity(contentId, contentType))
        }
    }

    override fun removeFavorite(contentId: String, contentType: String) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavorite(contentId, contentType)
        }
    }
}