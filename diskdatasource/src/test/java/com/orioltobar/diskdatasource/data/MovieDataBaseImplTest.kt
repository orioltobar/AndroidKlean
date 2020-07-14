package com.orioltobar.diskdatasource.data

import com.orioltobar.diskdatasource.DbMocks.getDbGenreModel
import com.orioltobar.diskdatasource.DbMocks.getDbModel
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.dao.MovieGenreDao
import com.orioltobar.diskdatasource.mappers.MovieDbMapper
import com.orioltobar.diskdatasource.mappers.MovieGenreDbMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieDataBaseImplTest {

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @MockK
    private lateinit var movieDao: MovieDao

    @MockK
    private lateinit var genreDao: MovieGenreDao

    @MockK
    private lateinit var movieDbMapper: MovieDbMapper

    @MockK
    private lateinit var movieGenreMapper: MovieGenreDbMapper

    private val dbDataSource by lazy {
        MovieDataBaseImpl(
            movieDao,
            genreDao,
            movieDbMapper,
            movieGenreMapper
        )
    }

    @Before
    fun setup() {
        coEvery { movieDao.getMovie(any()) } returns getDbModel()
        coEvery { movieDao.getMovies() } returns listOf(getDbModel(), getDbModel())
        coEvery { genreDao.getGenres() } returns listOf(getDbGenreModel(), getDbGenreModel())
        every { movieDbMapper.map(any()) } returns mockk()
        every { movieDbMapper.mapToDbModel(any()) } returns mockk()
        every { movieGenreMapper.map(any()) } returns mockk()
        every { movieGenreMapper.mapToDbModel(any()) } returns mockk()
    }

    @Test
    fun `getMoviePage() must call database getMoviePage() and map the result`() =
        runBlockingTest {
            // Given
            val id = 1L

            // When
            dbDataSource.getMovie(id)

            // Then
            coVerify(exactly = 1) {
                movieDao.getMovie(id)
                movieDbMapper.map(any())
            }
        }

    @Test
    fun `saveGenre() must call database setGenre()`() =
        runBlockingTest {
            // When
            dbDataSource.saveGenre(mockk())

            // Then
            coVerify(exactly = 1) {
                genreDao.setGenre(any())
            }
        }

    @Test
    fun `saveMovie() must call database insertWithTimestamp()`() =
        runBlockingTest {
            // When
            dbDataSource.saveMovie(mockk())

            // Then
            coVerify(exactly = 1) {
                movieDao.insertWithTimestamp(any())
            }
        }

    @Test
    fun `getGenres() must call database getGenres()`() =
        runBlockingTest {
            // When
            dbDataSource.getGenres()

            // Then
            coVerify(exactly = 1) {
                movieGenreMapper
                genreDao.getGenres()
            }
            coVerify(exactly = 2) {
                movieGenreMapper.map(any())
            }
        }

    @Test
    fun `getMovieList() must call database getMovies()`() =
        runBlockingTest {
            // When
            val pageId = 1
            dbDataSource.getMovieList(pageId)

            // Then
            coVerify(exactly = 1) {
                movieDao.getMovies()
            }
        }
}