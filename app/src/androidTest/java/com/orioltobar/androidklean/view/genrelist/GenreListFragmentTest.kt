package com.orioltobar.androidklean.view.genrelist

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
import com.orioltobar.domain.usecases.GetGenresListUseCase
import com.orioltobar.features.NewValue
import com.orioltobar.features.UiStatus
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GenreListFragmentTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @BindValue
    @MockK
    lateinit var mockUseCase: GetGenresListUseCase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var navHostController: NavHostController

    @Before
    fun setup() {
        navHostController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navHostController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun setMovieGenreListTest() {
        coEvery { mockUseCase.invoke() } returns Success((getGenreMockList() as NewValue).result)

        launchFragmentInHiltContainer<GenreListFragment>(navHost = navHostController)

        val expectedList = (getGenreMockList() as NewValue).result
        checkViewIsNotDisplayed(R.id.genreListProgressBar)
        checkRecyclerViewItemCount(R.id.genreListRecyclerView, expectedList.size)
        expectedList.forEachIndexed { index, element ->
            checkThatRecyclerViewItemHasText(
                R.id.genreListRecyclerView,
                index,
                element.name
            )
        }
    }

    @Test
    fun onGenreClickedTest() {
        coEvery { mockUseCase.invoke() } returns Success((getGenreMockList() as NewValue).result)

        launchFragmentInHiltContainer<GenreListFragment>(navHost = navHostController)

        performRecyclerViewItemClick(R.id.genreListRecyclerView, 0)

        assertEquals(navHostController.currentDestination?.id, R.id.movieListFragment)
    }

    @Test
    fun onLoadingMustShowLoadingProgressBarTest() {
        coEvery { mockUseCase.invoke() } returns Failure(ErrorModel("", ApiError.RequestError))

        launchFragmentInHiltContainer<GenreListFragment>(navHost = navHostController)

        checkViewIsDisplayed(R.id.genreListProgressBar)
    }

    private fun getGenreMockList(): UiStatus<ErrorModel, List<MovieGenreDetailModel>> =
        NewValue(
            listOf(
                MovieGenreDetailModel(12, "Action"),
                MovieGenreDetailModel(39, "Horror"),
                MovieGenreDetailModel(20, "Drama"),
                MovieGenreDetailModel(4, "Comedy"),
                MovieGenreDetailModel(59, "Western")
            )
        )
}