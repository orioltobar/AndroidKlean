package com.orioltobar.domain.usecases

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieListByGenreUseCase @Inject constructor(
    appDispatchers: AppDispatchers,
    private val repository: MovieRepository) {

    private val ioDispatcher = appDispatchers.io

    suspend operator fun invoke(genreId: Int) = withContext(ioDispatcher) {
        repository.getMovieListByGenre(genreId)
    }
}