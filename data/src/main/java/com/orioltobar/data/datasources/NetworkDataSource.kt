package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel

interface NetworkDataSource {

    suspend fun getMoviePage(pageId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMoviePageByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel>

    suspend fun getGenres(): Response<ErrorModel, MovieGenresModel>
}