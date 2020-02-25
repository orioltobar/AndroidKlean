package com.orioltobar.networkdatasource.api.data

import com.orioltobar.commons.Response
import com.orioltobar.commons.mapResponse
import com.orioltobar.data.datasources.NetworkDataSource
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.networkdatasource.api.mappers.MovieGenresMapper
import com.orioltobar.networkdatasource.api.mappers.MovieMapper
import com.orioltobar.networkdatasource.utils.safeApiCall
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieMapper: MovieMapper,
    private val movieGenreMapper: MovieGenresMapper
) : NetworkDataSource {

    override suspend fun getMoviePage(pageId: Int): Response<List<MovieModel>, ErrorModel> =
        safeApiCall { movieService.getMovieList(pageId) }.mapResponse {
            it.movieList.map(movieMapper::map)
        }

    override suspend fun getMoviePageByGenre(genreId: Int): Response<List<MovieModel>, ErrorModel> =
        safeApiCall {
            movieService.getMovieGenreList(genreId = genreId)
        }.mapResponse {
            it.movieList.map(movieMapper::map)
        }

    override suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel> =
        safeApiCall { movieService.getMovie(id) }.mapResponse(movieMapper::map)

    override suspend fun getGenres(): Response<MovieGenresModel, ErrorModel> =
        safeApiCall { movieService.getGenres() }.mapResponse(movieGenreMapper::map)
}