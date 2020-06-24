package com.orioltobar.androidklean.view.movielist

import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.di.launchFragmentInHiltContainer
import com.orioltobar.commons.Failure
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ApiError
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieListByGenreUseCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieListFragmentTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @BindValue
    @MockK
    lateinit var mockUseCase: GetMovieListByGenreUseCase

    private lateinit var navHostController: NavHostController

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        navHostController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navHostController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun setMovieListTest() {
        val expectedList = getMovieListMock()
        coEvery { mockUseCase(any()) } returns Success(getMovieListMock())

        launchFragmentInHiltContainer<MovieListFragment>(
            args = Bundle().apply { putInt("id", 12) },
            navHost = navHostController
        )

        checkViewIsNotDisplayed(R.id.movieListProgressBar)
        checkRecyclerViewItemCount(R.id.movieListRecyclerView, expectedList.size)
        expectedList.forEachIndexed { index, element ->
            checkThatRecyclerViewItemHasText(
                R.id.movieListRecyclerView,
                index,
                element.title
            )
        }
    }

    @Test
    fun onLoadingMustShowLoadingProgressBarTest() {
        coEvery { mockUseCase(any()) } returns Failure(ErrorModel("", ApiError.RequestError))

        launchFragmentInHiltContainer<MovieListFragment>(
            args = Bundle().apply { putInt("id", -1) },
            navHost = navHostController
        )

        Thread.sleep(
            500
        )
        checkViewIsDisplayed(R.id.movieListProgressBar)
    }

    private fun getMovieListMock(): List<MovieModel> {
        val genresModel = mockk<MovieGenresModel>()
        val movieModel = mockk<MovieModel>()
        every { genresModel.genre } returns listOf(MovieGenreDetailModel(12, "Action"))
        every { movieModel.id } returns 12345L
        every { movieModel.title } returns "Title test"
        every { movieModel.voteAverage } returns 5.0F
        every { movieModel.originalLanguage } returns "English"
        every { movieModel.genres } returns genresModel
        every { movieModel.releaseDate } returns "Jan 1979"
        every { movieModel.frontImageUrl } returns "https://image.tmdb.org/t/p/w300/6sjMsBcIuqU44GpG5tL33KUFOQR.jpg"

        return listOf(movieModel, movieModel, movieModel, movieModel)
    }
}