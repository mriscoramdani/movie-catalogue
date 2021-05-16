package com.riscodev.moviecat.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.riscodev.moviecat.BuildConfig
import com.riscodev.moviecat.data.source.local.LocalDataSource
import com.riscodev.moviecat.data.source.local.entity.FavoriteEntity
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.data.source.remote.ApiResponse
import com.riscodev.moviecat.data.source.remote.RemoteDataSource
import com.riscodev.moviecat.data.source.remote.response.MovieResponse
import com.riscodev.moviecat.data.source.remote.response.ShowResponse
import com.riscodev.moviecat.utils.AppExecutors
import com.riscodev.moviecat.vo.Resource

class FakeAppRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : AppDataSource {

    private var config: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(4)
        .setPageSize(4)
        .build()

    override fun getListMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> =
                LivePagedListBuilder(localDataSource.getListMovies(), config).build()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getListMovies()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieEntities = ArrayList<MovieEntity>()
                data.forEach {
                    val movie = MovieEntity(
                        it.id.toString(),
                        it.title,
                        it.overview,
                        it.releaseDate,
                        it.voteAverage,
                        it.status,
                        it.genresEntity.joinToString { genre ->
                            genre.name
                        },
                        BuildConfig.API_IMAGE_URL + it.posterPath
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
                remoteDataSource.getListShows()

            public override fun saveCallResult(data: List<ShowResponse>) {
                val showEntities = ArrayList<ShowEntity>()
                data.forEach {
                    val show = ShowEntity(
                        it.id.toString(),
                        it.originalName,
                        it.overview,
                        it.firstAirDate,
                        it.voteAverage,
                        it.status,
                        it.genres.joinToString { genre ->
                            genre.name
                        },
                        it.numberOfSeasons,
                        BuildConfig.API_IMAGE_URL + it.posterPath
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

            public override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    data.id.toString(),
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.voteAverage,
                    data.status,
                    data.genresEntity.joinToString { genre ->
                        genre.name
                    },
                    BuildConfig.API_IMAGE_URL + data.posterPath
                )
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getShow(id: String): LiveData<Resource<ShowEntity>> {
        return object : NetworkBoundResource<ShowEntity, ShowResponse>(appExecutors) {
            public override fun loadFromDB(): LiveData<ShowEntity> =
                localDataSource.getShow(id)

            public override fun createCall(): LiveData<ApiResponse<ShowResponse>> =
                remoteDataSource.getShow(id)

            public override fun saveCallResult(data: ShowResponse) {
                val show = ShowEntity(
                    data.id.toString(),
                    data.originalName,
                    data.overview,
                    data.firstAirDate,
                    data.voteAverage,
                    data.status,
                    data.genres.joinToString { genre ->
                        genre.name
                    },
                    data.numberOfSeasons,
                    BuildConfig.API_IMAGE_URL + data.posterPath
                )
                localDataSource.updateShow(show)
            }
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