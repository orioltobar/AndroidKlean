package com.orioltobar.data.repositories

import com.orioltobar.commons.Response
import com.orioltobar.data.datasources.MovieDataSource
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) :
    MovieRepository {

    override suspend fun getMovie(id: Long): Response<MovieModel>  {
        return dataSource.getMovie(id)
    }

    override fun getMovieFlow(): Flow<Response<MovieModel>> = flow {
        for(x in 0 until 10) {
            val id = Random.nextLong(1L, 999L)
            emit(dataSource.getMovie(id))
            delay(60000L)
        }
    }
}