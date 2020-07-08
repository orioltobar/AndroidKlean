package com.orioltobar.networkdatasource.api.data

import com.orioltobar.networkdatasource.api.models.MovieApiModel
import com.orioltobar.networkdatasource.api.models.MovieGenresApiModel
import com.orioltobar.networkdatasource.api.models.MovieListApiModel
import com.orioltobar.networkdatasource.api.models.MoviePageApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("3/discover/movie")
    suspend fun getMovieGenreList(
        @Query(POPULARITY) popularity: String = "popularity.desc",
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(INCLUDE_VIDEO) includeVideo: Boolean = false,
        @Query(PAGE_ID) pageId: Int = 1,
        @Query(GENRE_ID) genreId: Int
    ): MoviePageApiModel

    companion object {
        private const val MOVIE_ID = "movie_id"
        private const val POPULARITY = "sort_by"
        private const val INCLUDE_ADULT = "include_adult"
        private const val INCLUDE_VIDEO = "include_video"
        private const val PAGE_ID = "page"
        private const val GENRE_ID = "with_genres"
    }
}