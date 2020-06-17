package com.orioltobar.domain.usecases

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGenresListUseCase @Inject constructor(
    appDispatchers: AppDispatchers,
    private val movieRepository: MovieRepository) {

    private val ioDispatcher = appDispatchers.io

    suspend operator fun invoke(): Response<List<MovieGenreDetailModel>, ErrorModel> =
        withContext(ioDispatcher) {
            movieRepository.getMovieGenres()
        }
}