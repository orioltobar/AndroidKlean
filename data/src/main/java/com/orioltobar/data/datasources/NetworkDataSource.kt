package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel

interface NetworkDataSource {

    suspend fun getMoviePage(pageId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMoviePageByGender(genderId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel>

    suspend fun getGenres(): Response<MovieGenresModel, ErrorModel>
}