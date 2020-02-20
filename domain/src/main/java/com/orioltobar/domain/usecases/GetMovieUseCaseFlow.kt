package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieUseCaseFlow @Inject constructor(private val movieRepository: MovieRepository) {

    fun execute() = movieRepository.getMovieFlow()
}