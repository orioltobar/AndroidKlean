package com.orioltobar.data.repositories

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.commons.error.GenericError
import com.orioltobar.commons.singleSourceOfTruth
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.data.datasources.NetworkDataSource
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
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

    private var genres: List<MovieGenreDetailModel>? = null

    /**
     * Returns or initialize the movie genres list.
     */
    private suspend fun initOrReturnGenresArray(): List<MovieGenreDetailModel> {
        return genres ?: run {
            genres = getGenres()
            genres ?: emptyList()
        }
    }

    override suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel> {
        return singleSourceOfTruth(
            dbDataSource = {
                val movie = dbDataSource.getMovie(id)
                movie
            },
            networkDataSource = { dataSource.getMovie(id) },
            dbCallback = { apiResult ->
                dbDataSource.saveMovie(apiResult)
                val movie = dbDataSource.getMovie(id)
                movie
            }
        )
    }

    override fun getMovieFlow(): Flow<Response<ErrorModel, MovieModel>> = flow {
        for (x in 0 until 10) {
            val id = Random.nextLong(1L, 999L)
            emit(dataSource.getMovie(id))
            delay(60000L)
        }

    }

    override suspend fun getMovieList(pageId: Int): Response<ErrorModel, List<MovieModel>> {
        initOrReturnGenresArray()
        return singleSourceOfTruth(
            dbDataSource = { dbDataSource.getMovieList(pageId) },
            networkDataSource = { dataSource.getMoviePage(pageId) },
            dbCallback = { apiResult ->
                apiResult.map {
                    dbDataSource.saveMovie(it)
                }
                dbDataSource.getMovieList(pageId)
            }
        )
    }

    override suspend fun getMovieListByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>> {
        return singleSourceOfTruth(
            dbDataSource = { dbDataSource.getMoviePageByGenre(genreId) },
            networkDataSource = { dataSource.getMoviePageByGenre(genreId) },
            dbCallback = { apiResult ->
                apiResult.map { movie ->
                    dbDataSource.saveMovie(movie)
                }
                dbDataSource.saveGenrePage(genreId, 1, apiResult)
                dbDataSource.getMoviePageByGenre(genreId)
            }
        )
    }


    override suspend fun getMovieGenres(): Response<ErrorModel, List<MovieGenreDetailModel>> {
        val genres = getGenres()
        return if (genres.isNotEmpty()) {
            Success(genres)
        } else {
            Failure(ErrorModel("Genre list is empty.", GenericError()))
        }
    }

    override suspend fun saveMovieGenres(genres: List<MovieGenreDetailModel>) =
        genres.forEach {
            dbDataSource.saveGenre(it)
        }

    /**
     * Executes the genre call ASAP in order to store it in the database if they weren't.
     */
    private suspend fun getGenres(): List<MovieGenreDetailModel> {
        val genresModel = singleSourceOfTruth(
            dbDataSource = { dbDataSource.getGenres() },
            networkDataSource = { dataSource.getGenres() },
            dbCallback = { apiResult ->
                apiResult.genre.map { dbDataSource.saveGenre(it) }
                dbDataSource.getGenres()
            }
        )
        return if (genresModel is Success) {
            this.genres ?: run { this.genres = genresModel.result.genre }
            genresModel.result.genre
        } else {
            emptyList()
        }
    }
}