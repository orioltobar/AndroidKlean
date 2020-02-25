package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel

interface DbDataSource {

    suspend fun getMovieList(pageId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMoviePageByGenre(genreId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel>

    suspend fun saveMovie(movie: MovieModel)

    suspend fun getGenres(): Response<MovieGenresModel, ErrorModel>

    suspend fun saveGenre(genre: MovieGenreDetailModel)
}