package com.riscodev.moviecat.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.refEq
import com.nhaarman.mockitokotlin2.verify
import com.riscodev.moviecat.data.source.local.LocalDataSource
import com.riscodev.moviecat.data.source.local.entity.FavoriteEntity
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.data.source.remote.ApiResponse
import com.riscodev.moviecat.data.source.remote.RemoteDataSource
import com.riscodev.moviecat.data.source.remote.response.MovieResponse
import com.riscodev.moviecat.data.source.remote.response.ShowResponse
import com.riscodev.moviecat.utils.AppExecutors
import com.riscodev.moviecat.utils.DataDummy
import com.riscodev.moviecat.utils.LiveDataTestUtil
import com.riscodev.moviecat.utils.PagedListUtil
import com.riscodev.moviecat.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AppRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val appRepository = FakeAppRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id.toString()
    private val showResponses = DataDummy.generateRemoteDummyShows()
    private val showId = showResponses[0].id.toString()

    @Test
    fun getListMovies() {
        val response = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        response.value = ApiResponse.success(movieResponses)

        `when`(remote.getListMovies()).thenReturn(response)
        appRepository.getListMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(remote).getListMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getListShows() {
        val response = MutableLiveData<ApiResponse<List<ShowResponse>>>()
        response.value = ApiResponse.success(showResponses)

        `when`(remote.getListShows()).thenReturn(response)
        appRepository.getListMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(remote).getListMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        val dummyEntity = MutableLiveData<ApiResponse<MovieResponse>>()
        dummyEntity.value = ApiResponse.success(movieResponses[0])

        `when`(remote.getMovie(movieId)).thenReturn(dummyEntity)
        appRepository.getMovie(movieId)

        val movieEntity = Resource.success(dummyEntity)
        verify(remote).getMovie(movieId)
        assertNotNull(movieEntity)
        assertNotNull(movieEntity.data)
        assertEquals(dummyEntity, movieEntity.data)
    }

    @Test
    fun getShow() {
        val dummyEntity = MutableLiveData<ApiResponse<ShowResponse>>()
        dummyEntity.value = ApiResponse.success(showResponses[0])

        `when`(remote.getShow(showId)).thenReturn(dummyEntity)
        appRepository.getShow(showId)

        val showEntity = Resource.success(dummyEntity)
        verify(remote).getShow(showId)
        assertNotNull(showEntity)
        assertNotNull(showEntity.data)
        assertEquals(dummyEntity, showEntity.data)
    }

    @Test
    fun getListFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        appRepository.getListFavoriteMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getListFavoriteMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getListFavoriteShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getListFavoriteShows()).thenReturn(dataSourceFactory)
        appRepository.getListFavoriteShows()

        val showEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyShows()))
        verify(local).getListFavoriteShows()
        assertNotNull(showEntities.data)
        assertEquals(movieResponses.size.toLong(), showEntities.data?.size?.toLong())
    }

    @Test
    fun getFavorite() {
        val dummyEntity = MutableLiveData<FavoriteEntity>()
        dummyEntity.value = DataDummy.generateFavorite()

        val dummyContentId = "1"
        val dummyContentType = MovieEntity.TYPE
        `when`(local.getFavorite(dummyContentId, dummyContentType)).thenReturn(dummyEntity)

        val favoriteEntity =
            LiveDataTestUtil.getValue(local.getFavorite(dummyContentId, dummyContentType))
        verify(local).getFavorite(dummyContentId, dummyContentType)
        assertNotNull(favoriteEntity)
        assertEquals(dummyEntity.value, favoriteEntity)
    }

    @Test
    fun saveFavorite() {
        val dummyContentId = "1"
        val dummyContentType = MovieEntity.TYPE

        val favoriteEntity = FavoriteEntity(dummyContentId, dummyContentType)
        `when`(local.insertFavorite(favoriteEntity)).then { Unit }
        appRepository.saveFavorite(dummyContentId, dummyContentType)
        verify(local).insertFavorite(refEq(favoriteEntity))
    }

    @Test
    fun removeFavorite() {
        val dummyContentId = "1"
        val dummyContentType = MovieEntity.TYPE

        `when`(local.deleteFavorite(dummyContentId, dummyContentType)).then { Unit }
        appRepository.removeFavorite(dummyContentId, dummyContentType)
        verify(local).deleteFavorite(dummyContentId, dummyContentType)
    }

}