package com.orioltobar.diskdatasource.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.orioltobar.diskdatasource.Helper.testBlocking
import com.orioltobar.diskdatasource.database.AppDataBase
import com.orioltobar.diskdatasource.models.MovieGenreDbModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovieGenreDaoTest {

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
    fun insertAndGetGenreToDbTest() {
        testBlocking {
            val dbModel = MovieGenreDbModel(12, "Action", "", 123456L)

            appDataBase.movieGenreDao().setGenre(dbModel)
            val result = appDataBase.movieGenreDao().getGenres()

            Assert.assertTrue(result.isNotEmpty())
        }
    }

    @Test
    fun removeItemFromDbTest() {
        testBlocking {
            val dbModel = MovieGenreDbModel(12, "Action", "", 12345L)

            appDataBase.movieGenreDao().setGenre(dbModel)
            appDataBase.movieGenreDao().deleteGenre(dbModel)
            val result = appDataBase.movieGenreDao().getGenres()

            Assert.assertTrue(result.isEmpty())
        }
    }
}