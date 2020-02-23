package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(pageId: Int) = withContext(Dispatchers.IO) {
        movieRepository.getMovieList(pageId)
    }
}