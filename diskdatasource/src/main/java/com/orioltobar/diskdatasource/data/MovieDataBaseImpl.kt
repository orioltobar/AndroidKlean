package com.orioltobar.diskdatasource.data

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.diskdatasource.Cache
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import javax.inject.Inject

class MovieDataBaseImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieDbMapper: MovieDbMapper
) : DbDataSource {

    override suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel> =
        movieDao.getMovie(id).let {
            // Check null is necessary because if model doesn't exist in db, it is null.
            @Suppress("SENSELESS_COMPARISON")
            if (it == null) {
                Failure(ErrorModel(""))
            } else {
                Cache.checkTimestampCache(it.timeStamp, movieDbMapper.map(it))
            }
        }

    override suspend fun saveMovie(movie: MovieModel) {
        return movieDao.insertWithTimestamp(movieDbMapper.mapToDbModel(movie))
    }
}