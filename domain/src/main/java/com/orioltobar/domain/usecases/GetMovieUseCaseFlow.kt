package com.orioltobar.domain.usecases

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetMovieUseCaseFlow @Inject constructor(
    appDispatchers: AppDispatchers,
    private val movieRepository: MovieRepository) {

    private val ioDispatcher = appDispatchers.io

    fun execute() = movieRepository.getMovieFlow().flowOn(ioDispatcher)
}