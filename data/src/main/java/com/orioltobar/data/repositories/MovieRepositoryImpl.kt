package com.orioltobar.data.repositories

import com.orioltobar.commons.*
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.data.datasources.NetworkDataSource
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val dbDataSource: DbDataSource
) :
    MovieRepository {

    override suspend fun getMovie(id: Long): Response<MovieModel> {
        return singleSourceOfTruth(id)
    }

    override fun getMovieFlow(): Flow<Response<MovieModel>> = flow {
        for (x in 0 until 10) {
            val id = Random.nextLong(1L, 999L)
            emit(dataSource.getMovie(id))
            delay(60000L)
        }
    }

    /**
     * Single source of truth pattern. Database serves as SSOT in this case. Network calls are used
     * to store the values in the database.
     */
    private suspend fun singleSourceOfTruth(id: Long): Response<MovieModel> =
        dbDataSource.getMovie(id).valueOrNull()?.let { dbResult -> Success(dbResult) }
            ?: run {
                dataSource.getMovie(id).either(
                    onSuccess = { apiResult ->
                        dbDataSource.saveMovie(apiResult)
                        dbDataSource.getMovie(id)
                    },
                    onFailure = { error -> Failure(error) }
                )
            }
}