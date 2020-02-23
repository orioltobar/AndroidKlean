package com.orioltobar.diskdatasource.data

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.diskdatasource.Cache
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.dao.MovieGenreDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.diskdatasource.mappers.MovieGenreDbMapper
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel
import javax.inject.Inject

class MovieDataBaseImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieDbMapper: MovieDbMapper,
    private val movieGenreDbMapper: MovieGenreDbMapper
) : DbDataSource {

    override suspend fun getMovieList(pageId: Int): Response<List<MovieModel>, ErrorModel> {
        return movieDao.getMovies().takeIf { it.isNotEmpty() }?.let { list ->
            val movieList: MutableList<MovieModel> = mutableListOf()
            for (model in list) {
                val movie = Cache.checkTimestampCache(model.timeStamp, movieDbMapper.map(model))
                if (movie is Success) {
                    val genres = movieGenreDao.getGenres()
                    val genresFiltered = movie.result.genreIds.flatMap { ids ->
                        genres.filter { it.id == ids }.map(movieGenreDbMapper::map)
                    }
                    movie.result.genres = MovieGenresModel(genresFiltered)
                    movieList.add(movie.result)
                } else {
                    movieList.clear()
                    break
                }
            }
            if (movieList.isNotEmpty()) {
                Success(movieList.toList())
            } else {
                Failure(ErrorModel(""))
            }
        } ?: Failure(ErrorModel(""))
    }

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

    override suspend fun saveGenre(genre: MovieGenreDetailModel) =
        movieGenreDao.setGenre(movieGenreDbMapper.mapToDbModel(genre))

    override suspend fun getGenres(): Response<MovieGenresModel, ErrorModel> =
        movieGenreDao.getGenres()
            .takeIf { it.isNotEmpty() }
            ?.let { list ->
                Success(MovieGenresModel(list.map(movieGenreDbMapper::map)))
            } ?: Failure(ErrorModel(""))
}