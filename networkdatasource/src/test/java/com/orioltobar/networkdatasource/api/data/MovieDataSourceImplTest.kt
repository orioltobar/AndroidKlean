package com.orioltobar.networkdatasource.api.data

import com.google.gson.Gson
import com.orioltobar.commons.Constants
import com.orioltobar.commons.MockObjects
import com.orioltobar.networkdatasource.api.mappers.MovieGenresMapper
import com.orioltobar.networkdatasource.api.mappers.MovieMapper
import com.orioltobar.networkdatasource.api.models.MovieListApiModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDataSourceImplTest {

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @MockK
    private lateinit var movieService: MovieService

    @MockK
    private lateinit var movieMapper: MovieMapper

    @MockK
    private lateinit var movieGenreMapper: MovieGenresMapper

    private val dataSource by lazy {
        MovieDataSourceImpl(
            movieService,
            movieMapper,
            movieGenreMapper
        )
    }

    @Before
    fun setup() {
        val mockMovieListApiModel =
            Gson().fromJson(MockObjects.movieListJson, MovieListApiModel::class.java)

        coEvery { movieService.getMovieList(any()) } returns mockMovieListApiModel
        coEvery { movieService.getMovieGenreList(genreId = any()) } returns mockMovieListApiModel
        every { movieMapper.map(any()) } returns mockk()
    }

    @Test
    fun `getMoviePage() must call API service getMoviePage() and map the result`() =
        runBlockingTest {
            // Given
            val pageId = 1

            // When
            dataSource.getMoviePage(pageId)

            // Then
            coVerify(exactly = 1) {
                movieService.getMovieList(pageId)
            }
            coVerify {
                movieMapper.map(any())
            }
        }

    @Test
    fun `getMoviePageByGenre() must call API service getMoviePageByGenre() and map the result`() =
        runBlockingTest {
            // Given
            val genreId = Constants.DEFAULT_GENRE_ID

            // When
            dataSource.getMoviePageByGenre(genreId)

            // Then
            coVerify(exactly = 1) {
                movieService.getMovieGenreList(genreId = genreId)
            }
            coVerify {
                movieMapper.map(any())
            }
        }
}