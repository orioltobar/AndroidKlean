package com.orioltobar.domain.usecases

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveMovieGenresUseCase @Inject constructor(
    appDispatchers: AppDispatchers,
    private val repository: MovieRepository
) {

    private val ioDispatcher = appDispatchers.io

    suspend operator fun invoke(genres: List<MovieGenreDetailModel>) = withContext(ioDispatcher) {
        repository.saveMovieGenres(genres)
    }
}