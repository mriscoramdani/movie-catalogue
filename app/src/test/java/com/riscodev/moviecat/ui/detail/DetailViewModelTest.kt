package com.riscodev.moviecat.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.FavoriteEntity
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.ui.main.content.movie.MovieViewModelTest
import com.riscodev.moviecat.utils.DataDummy
import com.riscodev.moviecat.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyShow = DataDummy.generateDummyShows()[0]
    private val movieId = dummyMovie.movieId
    private val showId = dummyShow.showId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var showObserver: Observer<Resource<ShowEntity>>

    @Mock
    private lateinit var favoriteObserver: Observer<FavoriteEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(appRepository)
    }

    @Test
    fun `getMovie should be success`() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(appRepository.getMovie(movieId)).thenReturn(expected)

        viewModel.setSelectedMovie(movieId)
        viewModel.movie.observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movie.value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getShow should be success`() {
        val expected = MutableLiveData<Resource<ShowEntity>>()
        expected.value = Resource.success(dummyShow)

        `when`(appRepository.getShow(showId)).thenReturn(expected)

        viewModel.setSelectedShow(showId)
        viewModel.show.observeForever(showObserver)

        verify(showObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.show.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getFavorite should be success`() {
        val expected = MutableLiveData<FavoriteEntity>()
        expected.value = DataDummy.generateFavorite()

        val dummyContentId = "1"
        val dummyContentType = MovieEntity.TYPE
        `when`(appRepository.getFavorite(dummyContentId, dummyContentType)).thenReturn(expected)

        viewModel.getFavorite(dummyContentId, dummyContentType).observeForever(favoriteObserver)

        verify(favoriteObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavorite(dummyContentId, dummyContentType).value

        assertEquals(expectedValue, actualValue)
    }
}