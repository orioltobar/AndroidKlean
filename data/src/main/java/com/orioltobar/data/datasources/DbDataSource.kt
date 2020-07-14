package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel

interface DbDataSource {

    suspend fun getMovieList(pageId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMoviePageByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel>

    suspend fun saveMovie(movie: MovieModel)

    suspend fun saveGenrePage(genreId: Int, page: Int, movies: List<MovieModel>)

    suspend fun getGenres(): Response<ErrorModel, MovieGenresModel>

    suspend fun saveGenre(genre: MovieGenreDetailModel)
}