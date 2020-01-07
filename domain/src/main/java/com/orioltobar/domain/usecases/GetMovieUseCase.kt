package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun execute(id: Long) = movieRepository.getMovie(id)
}