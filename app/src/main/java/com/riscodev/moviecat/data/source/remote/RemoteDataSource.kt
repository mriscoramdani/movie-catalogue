package com.riscodev.moviecat.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riscodev.moviecat.data.source.remote.response.DataResponse
import com.riscodev.moviecat.data.source.remote.response.MovieResponse
import com.riscodev.moviecat.data.source.remote.response.ShowResponse
import com.riscodev.moviecat.data.source.remote.retrofit.RemoteDao
import com.riscodev.moviecat.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(
    private val remoteDao: RemoteDao,
    private val apiRequestKey: String
) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(remoteDao: RemoteDao, apiRequestKey: String): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(remoteDao, apiRequestKey).apply { instance = this }
            }
    }

    fun getListMovie(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        remoteDao.getMovies(apiRequestKey).apply {
            enqueue(object : Callback<DataResponse<MovieResponse>> {
                override fun onResponse(
                    call: Call<DataResponse<MovieResponse>>,
                    response: Response<DataResponse<MovieResponse>>
                ) {
                    val data = response.body()
                    if (data != null)
                        resultMovie.value = ApiResponse.success(data.results)
                    else
                        resultMovie.value = ApiResponse.empty("", emptyList())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DataResponse<MovieResponse>>, t: Throwable) {
                    resultMovie.value = ApiResponse.error(t.toString(), emptyList())
                    EspressoIdlingResource.decrement()
                }
            })
        }
        return resultMovie
    }

    fun getListShow(): LiveData<ApiResponse<List<ShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultShow = MutableLiveData<ApiResponse<List<ShowResponse>>>()

        remoteDao.getShows(apiRequestKey).apply {
            enqueue(object : Callback<DataResponse<ShowResponse>> {
                override fun onResponse(
                    call: Call<DataResponse<ShowResponse>>,
                    response: Response<DataResponse<ShowResponse>>
                ) {
                    val data = response.body()
                    if (data != null)
                        resultShow.value = ApiResponse.success(data.results)
                    else
                        resultShow.value = ApiResponse.empty("", emptyList())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DataResponse<ShowResponse>>, t: Throwable) {
                    resultShow.value = ApiResponse.error(t.toString(), emptyList())
                    EspressoIdlingResource.decrement()
                }
            })
        }
        return resultShow
    }

    fun getMovie(movieId: String): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()

        remoteDao.getMovie(movieId, apiRequestKey).apply {
            enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val data = response.body()
                    if (data != null)
                        result.value = ApiResponse.success(data)
                    else
                        result.value = ApiResponse.empty("", MovieResponse())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    result.value = ApiResponse.error(t.toString(), MovieResponse())
                    EspressoIdlingResource.decrement()
                }
            })
        }
        return result
    }

    fun getShow(movieId: String): LiveData<ApiResponse<ShowResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<ShowResponse>>()

        remoteDao.getShow(movieId, apiRequestKey).apply {
            enqueue(object : Callback<ShowResponse> {
                override fun onResponse(
                    call: Call<ShowResponse>,
                    response: Response<ShowResponse>
                ) {
                    val data = response.body()
                    if (data != null)
                        result.value = ApiResponse.success(data)
                    else
                        result.value = ApiResponse.empty("", ShowResponse())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<ShowResponse>, t: Throwable) {
                    result.value = ApiResponse.error(t.toString(), ShowResponse())
                    EspressoIdlingResource.decrement()
                }
            })
        }
        return result
    }
}