package com.orioltobar.diskdatasource.data

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.commons.Cache
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.dao.MovieGenreDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.diskdatasource.mappers.MovieGenreDbMapper
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel
import javax.inject.Inject
import com.orioltobar.commons.error.DbError.QueryError
import com.orioltobar.commons.error.DbError.CacheError
import com.orioltobar.commons.flatMap
import com.orioltobar.data.datasources.CacheDataSource
import java.util.*

class MovieDataBaseImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieDbMapper: MovieDbMapper,
    private val movieGenreDbMapper: MovieGenreDbMapper,
    private val cacheDataSourceImpl: CacheDataSource
) : DbDataSource {

    override suspend fun getMovieList(pageId: Int): Response<ErrorModel, List<MovieModel>> {
        return movieDao.getMovies().takeIf { it.isNotEmpty() }?.let { list ->
            checkCache(list).flatMap { Success(it.map(movieDbMapper::map)) }
        } ?: Failure(ErrorModel("Error in MovieDataBase", QueryError))
    }

    override suspend fun getMoviePageByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>> {
        return movieDao.getMoviesByGenre(genreId).takeIf { it.isNotEmpty() }?.let { list ->
            checkCache(list).flatMap { Success(it.map(movieDbMapper::map)) }
        } ?: Failure(ErrorModel("Error in MovieDataBase", QueryError))
    }

    override suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel> =
        movieDao.getMovie(id)?.let { movie ->
            checkCache(movie).flatMap { Success(movieDbMapper.map(movie)) }
        } ?: run { Failure(ErrorModel("", QueryError)) }

    override suspend fun saveMovie(movie: MovieModel) {
        return movieDao.insertWithTimestamp(movieDbMapper.mapToDbModel(movie))
    }

    override suspend fun saveGenre(genre: MovieGenreDetailModel) =
        movieGenreDao.setGenre(movieGenreDbMapper.mapToDbModel(genre))

    override suspend fun getGenres(): Response<ErrorModel, MovieGenresModel> =
        movieGenreDao.getGenres()
            .takeIf { it.isNotEmpty() }
            ?.let { list ->
                val result = checkCache(list)
                result.flatMap {
                    Success(MovieGenresModel(it.map(movieGenreDbMapper::map)))
                }
            } ?: Failure(ErrorModel("Error in MovieDataBase", QueryError))

    /**
     * Checks if the cache is expired.
     */
    private fun <T> checkCache(model: T): Response<ErrorModel, T> {
        val cacheTimestamp = cacheDataSourceImpl.getCacheTimestamp()
        val cacheResult = Cache.checkTimestampCache(cacheTimestamp)
        return if (cacheResult) {
            Success(model)
        } else {
            // Set new cache value.
            cacheDataSourceImpl.saveCacheTimestamp(Date().time)
            Failure(ErrorModel(("Cache Expired"), CacheError))
        }
    }
}