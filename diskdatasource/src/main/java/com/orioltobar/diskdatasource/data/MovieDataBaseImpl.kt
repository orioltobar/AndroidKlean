package com.orioltobar.diskdatasource.data

import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.domain.models.movie.MovieModel
import javax.inject.Inject

class MovieDataBaseImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieDbMapper: MovieDbMapper
) : DbDataSource {

    override suspend fun getMovie(id: Long): Response<MovieModel> =
        Success(movieDao.getMovie(id).let { movieDbMapper.map(it) })
}