package com.riscodev.moviecat.data.source.remote.retrofit

import com.riscodev.moviecat.data.source.remote.response.DataResponse
import com.riscodev.moviecat.data.source.remote.response.MovieResponse
import com.riscodev.moviecat.data.source.remote.response.ShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDao {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String?): Call<DataResponse<MovieResponse>>

    @GET("tv/popular")
    fun getShows(@Query("api_key") apiKey: String?): Call<DataResponse<ShowResponse>>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("tv/{tv_id}")
    fun getShow(@Path("tv_id") tvId: String?, @Query("api_key") apiKey: String): Call<ShowResponse>
}