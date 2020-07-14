package com.orioltobar.diskdatasource.data

import com.orioltobar.commons.Cache
import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.DbError.CacheError
import com.orioltobar.commons.error.DbError.QueryError
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.commons.flatMap
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.dao.MovieGenreDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.diskdatasource.mappers.MovieGenreDbMapper
import com.orioltobar.diskdatasource.models.MovieGenreListDbModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel
import java.util.*
import javax.inject.Inject

class MovieDataBaseImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieDbMapper: MovieDbMapper,
    private val movieGenreDbMapper: MovieGenreDbMapper
) : DbDataSource {

    override suspend fun getMovieList(pageId: Int): Response<ErrorModel, List<MovieModel>> {
        return movieDao.getMovies().takeIf { it.isNotEmpty() }?.let { list ->
            Success(list.map(movieDbMapper::map))
        } ?: Failure(ErrorModel("List not found.", QueryError))
    }

    override suspend fun getMoviePageByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>> {
        return movieGenreDao.getGenreListPage(genreId)?.let { page ->
            checkCache(page, page.timestamp).flatMap {
                val movies = page.movies.mapNotNull { (getMovie(it) as? Success)?.result }
                Success(movies)
            }
        } ?: Failure(ErrorModel("List not found.", QueryError))
    }

    override suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel> =
        movieDao.getMovie(id)?.let { dbMovie ->
            checkCache(dbMovie, dbMovie.timeStamp).flatMap {
                val movie = movieDbMapper.map(dbMovie)
                getMovieWithGenres(movie)
                Success(movie)
            }
        } ?: run { Failure(ErrorModel("Movie not found.", QueryError)) }

    override suspend fun saveMovie(movie: MovieModel) {
        return movieDao.insertWithTimestamp(movieDbMapper.mapToDbModel(movie))
    }

    override suspend fun saveGenrePage(genreId: Int, page: Int, movies: List<MovieModel>) {
        val genreList = MovieGenreListDbModel(
            genreId,
            page,
            movies.map { it.id },
            Date().time
        )
        movieGenreDao.saveGenreListPage(genreList)
    }

    override suspend fun saveGenre(genre: MovieGenreDetailModel) =
        movieGenreDao.setGenre(movieGenreDbMapper.mapToDbModel(genre))

    override suspend fun getGenres(): Response<ErrorModel, MovieGenresModel> =
        movieGenreDao.getGenres()
            .takeIf { it.isNotEmpty() }
            ?.let { list ->
                val result = checkCache(list, list.getOrNull(0)?.timestamp ?: 0L)
                result.flatMap {
                    Success(MovieGenresModel(it.map(movieGenreDbMapper::map)))
                }
            } ?: Failure(ErrorModel("Error in MovieDataBase", QueryError))

    private suspend fun getMovieWithGenres(movie: MovieModel) {
        val genreList = movie.genreIds.map { id ->
            movieGenreDbMapper.map(movieGenreDao.getGenre(id))
        }
        movie.genres = MovieGenresModel(genreList)
    }

    /**
     * Checks if the cache is expired.
     */
    private fun <T> checkCache(model: T, timestamp: Long): Response<ErrorModel, T> {
        val cacheResult = Cache.checkTimestampCache(timestamp)
        return if (cacheResult) {
            Success(model)
        } else {
            Failure(ErrorModel(("Cache Expired"), CacheError))
        }
    }
}