package com.riscodev.moviecat.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
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

    @Before
    fun setUp() {
        viewModel = DetailViewModel(appRepository)
        viewModel.setSelectedMovie(movieId)
        viewModel.setSelectedShow(showId)
    }

    @Test
    fun `setSelectedMovie should be success`() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(appRepository.getMovie(movieId)).thenReturn(expected)

        viewModel.movie.observeForever(movieObserver)
        verify(movieObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movie.value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `setSelectedShow should be success`() {
        val expected = MutableLiveData<Resource<ShowEntity>>()
        expected.value = Resource.success(dummyShow)

        `when`(appRepository.getShow(showId)).thenReturn(expected)

        viewModel.show.observeForever(showObserver)

        verify(showObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.show.value

        assertEquals(expectedValue, actualValue)
    }
}