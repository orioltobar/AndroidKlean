package com.orioltobar.data.repositories

import com.orioltobar.commons.*
import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.data.datasources.NetworkDataSource
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
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

    override suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel> {
        val genresList = initOrReturnGenresArray()
        return singleSourceOfTruth(
            dbDataSource = {
                val movie = dbDataSource.getMovie(id)
                if (movie is Success) {
                    movie.result.genres = getSelectedGenresFromList(movie.result, genresList)
                }
                movie
            },
            networkDataSource = { dataSource.getMovie(id) },
            dbCallback = { apiResult ->
                dbDataSource.saveMovie(apiResult)
                val movie = dbDataSource.getMovie(id)
                if (movie is Success) {
                    movie.result.genres = getSelectedGenresFromList(movie.result, genresList)
                }
                movie
            }
        )
    }

    override fun getMovieFlow(): Flow<Response<MovieModel, ErrorModel>> = flow {
        for (x in 0 until 10) {
            val id = Random.nextLong(1L, 999L)
            emit(dataSource.getMovie(id))
            delay(60000L)
        }

    }

    override suspend fun getMovieList(pageId: Int): Response<List<MovieModel>, ErrorModel> {
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

    override suspend fun getMovieListByGender(genderId: Int): Response<List<MovieModel>, ErrorModel> {
        initOrReturnGenresArray()
        // TODO: Fix get movie SingleSourceOfTruth.
        return dataSource.getMoviePageByGender(genderId)
//        return singleSourceOfTruth(
//            dbDataSource = { dbDataSource.getMoviePageByGender(genderId) },
//            networkDataSource = { dataSource.getMoviePageByGender(genderId) },
//            dbCallback = { apiResult ->
//                apiResult.map {
//                    dbDataSource.saveMovie(it)
//                }
//                dbDataSource.getMoviePageByGender(genderId)
//            }
//        )
    }

    override suspend fun getMovieGenres(): Response<List<MovieGenreDetailModel>, ErrorModel> {
        val genres = getGenres()
        return if (genres.isNotEmpty()) {
            Success(genres)
        } else {
            Failure(ErrorModel("Genre list is empty."))
        }
    }

    /**
     * Executes the gender call ASAP in order to store it in the database if they weren't.
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

    /**
     * Retrieves the main genre of the [movie] using a [genres] list.
     */
    private fun getSelectedGenresFromList(
        movie: MovieModel,
        genres: List<MovieGenreDetailModel>
    ): MovieGenresModel {
        val filteredList = genres.filter { it.id == movie.mainGenreId }
        return MovieGenresModel(filteredList)
    }
}