package com.orioltobar.domain.usecases

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGenresListUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun execute(): Response<List<MovieGenreDetailModel>, ErrorModel> =
        withContext(Dispatchers.IO) {
            movieRepository.getMovieGenres()
        }
}