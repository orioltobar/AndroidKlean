package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun execute(id: Long) = withContext(Dispatchers.IO) { movieRepository.getMovie(id) }
}