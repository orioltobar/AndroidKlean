package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(pageId: Int) = movieRepository.getMovieList(pageId)
}