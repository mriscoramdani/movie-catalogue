package com.riscodev.moviecat.ui.main.content.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.riscodev.moviecat.data.AppRepository
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.utils.DataDummy
import com.riscodev.moviecat.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest {

    private lateinit var viewModel: ShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<ShowEntity>>>

    @Mock
    private lateinit var observer2: Observer<PagedList<ShowEntity>>

    @Before
    fun setUp() {
        viewModel = ShowViewModel(appRepository)
    }

    @Test
    fun `getShows should be success`() {
        val shows = PagedTestDataSources.snapshot(DataDummy.generateDummyShows())
        val expected = MutableLiveData<Resource<PagedList<ShowEntity>>>()
        expected.value = Resource.success(shows)

        Mockito.`when`(appRepository.getListShows()).thenReturn(expected)

        viewModel.getShows().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getShows().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `getShows should be success but data is empty`() {
        val shows = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<ShowEntity>>>()
        expected.value = Resource.success(shows)

        Mockito.`when`(appRepository.getListShows()).thenReturn(expected)

        viewModel.getShows().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val actualDataSize = viewModel.getShows().value?.data?.size
        assertTrue("Size must be 0, actual is $actualDataSize", actualDataSize == 0)
    }

    @Test
    fun `getFavoriteShows should be success`() {
        val shows = PagedTestDataSources.snapshot(DataDummy.generateDummyShows())
        val expected = MutableLiveData<PagedList<ShowEntity>>()
        expected.value = shows

        Mockito.`when`(appRepository.getListFavoriteShows()).thenReturn(expected)

        viewModel.getFavoriteShows().observeForever(observer2)
        Mockito.verify(observer2).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteShows().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteShows should be success but data is empty`() {
        val shows = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<PagedList<ShowEntity>>()
        expected.value = shows

        Mockito.`when`(appRepository.getListFavoriteShows()).thenReturn(expected)

        viewModel.getFavoriteShows().observeForever(observer2)
        Mockito.verify(observer2).onChanged(expected.value)

        val actualDataSize = viewModel.getFavoriteShows().value?.size
        assertTrue("Size must be 0, actual is $actualDataSize", actualDataSize == 0)
    }

    class PagedTestDataSources private constructor(private val items: List<ShowEntity>) :
        PositionalDataSource<ShowEntity>() {
        companion object {
            fun snapshot(items: List<ShowEntity> = listOf()): PagedList<ShowEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<ShowEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ShowEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}