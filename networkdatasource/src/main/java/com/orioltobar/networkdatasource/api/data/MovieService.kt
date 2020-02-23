package com.orioltobar.networkdatasource.api.data

import com.orioltobar.networkdatasource.api.models.MovieApiModel
import com.orioltobar.networkdatasource.api.models.MovieGenresApiModel
import com.orioltobar.networkdatasource.api.models.MovieListApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("3/movie/{$MOVIE_ID}")
    suspend fun getMovie(
        @Path(MOVIE_ID) movieId: Long
    ): MovieApiModel

    @GET("3/list/{$PAGE_ID}")
    suspend fun getMovieList(
        @Path(PAGE_ID) listId: Int
    ): MovieListApiModel

    @GET("3/genre/movie/list")
    suspend fun getGenres(): MovieGenresApiModel

    companion object {
        private const val MOVIE_ID = "movie_id"
        private const val PAGE_ID = "list_id"
    }
}