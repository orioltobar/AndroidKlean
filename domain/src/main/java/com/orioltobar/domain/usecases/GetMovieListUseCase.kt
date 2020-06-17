package com.orioltobar.domain.usecases

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    appDispatchers: AppDispatchers,
    private val movieRepository: MovieRepository
) {

    private val ioDispatcher = appDispatchers.io

    suspend operator fun invoke(pageId: Int) = withContext(ioDispatcher) {
        movieRepository.getMovieList(pageId)
    }
}