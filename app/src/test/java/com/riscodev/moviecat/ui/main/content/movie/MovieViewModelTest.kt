package com.riscodev.moviecat.ui.main.content.movie

import android.graphics.Movie
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
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
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel : MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observer2: Observer<PagedList<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(appRepository)
    }

    @Test
    fun `getMovies should be success`() {
        val movies = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(movies)

        `when`(appRepository.getListMovies()).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `getMovies should be success but data is empty`() {
        val movies = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(movies)

        `when`(appRepository.getListMovies()).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualDataSize = viewModel.getMovies().value?.data?.size
        assertTrue("Size must be 0, actual is $actualDataSize", actualDataSize == 0)
    }

    @Test
    fun `getFavoriteMovies should be success`() {
        val movies = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = movies

        `when`(appRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer2)
        verify(observer2).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteMovies should be success but data is empty`() {
        val movies = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = movies

        `when`(appRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer2)
        verify(observer2).onChanged(expected.value)

        val actualDataSize = viewModel.getFavoriteMovies().value?.size
        assertTrue("Size must be 0, actual is $actualDataSize", actualDataSize == 0)
    }

    class PagedTestDataSources private constructor(private val items: List<MovieEntity>) : PositionalDataSource<MovieEntity>() {
        companion object {
            fun snapshot(items: List<MovieEntity> = listOf()): PagedList<MovieEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MovieEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}