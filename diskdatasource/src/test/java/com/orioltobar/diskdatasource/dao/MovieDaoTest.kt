package com.orioltobar.diskdatasource.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.orioltobar.diskdatasource.DbMocks.getDbModel
import com.orioltobar.diskdatasource.Helper
import com.orioltobar.diskdatasource.database.AppDataBase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovieDaoTest {

    private lateinit var appDataBase: AppDataBase

    @Before
    fun setup() {
        appDataBase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDataBase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        appDataBase.close()
    }

    @Test
    fun insertMovieWithTimeStampAndGetTheResultTest() {
        Helper.testBlocking {
            val dbModel = getDbModel()

            appDataBase.movieDao().insertWithTimestamp(dbModel)
            val result = appDataBase.movieDao().getMovie(dbModel.id)

            assertEquals(dbModel.id, result?.id)
        }
    }

    @Test
    fun deleteMovieWithTimeStampAndGetTheResultTest() {
        Helper.testBlocking {
            val dbModel = getDbModel()

            appDataBase.movieDao().insertWithTimestamp(dbModel)

            appDataBase.movieDao().deleteMovie(dbModel)
            val result = appDataBase.movieDao().getMovie(dbModel.id)

            assertNull(result)
        }
    }

    @Test
    fun getMovieListTest() {
        Helper.testBlocking {
            val movie1 = getDbModel()
            val movie2 = getDbModel(id = 2)
            val movie3 = getDbModel(id = 3)
            val movie4 = getDbModel(id = 4)

            appDataBase.movieDao().insertWithTimestamp(movie1)
            appDataBase.movieDao().insertWithTimestamp(movie2)
            appDataBase.movieDao().insertWithTimestamp(movie3)
            appDataBase.movieDao().insertWithTimestamp(movie4)

            val result = appDataBase.movieDao().getMovies()

            assertEquals(4, result.size)
        }
    }

    @Test
    fun `Get movies list by genre test`() {
        Helper.testBlocking {
            val id = 14
            val movie1 = getDbModel(genreList = listOf(14,19))
            val movie2 = getDbModel(genreList = listOf(14,20), id = 2)
            val movie3 = getDbModel(genreList= listOf(19,49), id = 3)
            val movie4 = getDbModel(genreList = listOf(30,49), id = 4)

            appDataBase.movieDao().insertWithTimestamp(movie1)
            appDataBase.movieDao().insertWithTimestamp(movie2)
            appDataBase.movieDao().insertWithTimestamp(movie3)
            appDataBase.movieDao().insertWithTimestamp(movie4)

            val result = appDataBase.movieDao().getMoviesByGenre(id)

            assertEquals(2, result.size)
        }
    }
}