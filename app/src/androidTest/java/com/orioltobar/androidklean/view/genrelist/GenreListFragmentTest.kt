package com.orioltobar.androidklean.view.genrelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.rule.ActivityTestRule
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.base.MockActivity
import com.orioltobar.androidklean.di.TestViewModelModule
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.features.NewValue
import com.orioltobar.features.UiStatus
import com.orioltobar.features.viewmodel.MovieGenresViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.android.synthetic.main.genre_list_fragment.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GenreListFragmentTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    private lateinit var genreListFragment: GenreListFragment

    @MockK
    private lateinit var movieGenresViewModelMock: MovieGenresViewModel

    private val _genreLiveData =
        MutableLiveData<UiStatus<List<MovieGenreDetailModel>, ErrorModel>>()
    private val genreLiveData: LiveData<UiStatus<List<MovieGenreDetailModel>, ErrorModel>>
        get() = _genreLiveData

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val activityRule = ActivityTestRule<MockActivity>(MockActivity::class.java, false, false)

    lateinit var navHostController: NavHostController

    lateinit var scenario: FragmentScenario<GenreListFragment>

    @Before
    fun setup() {
        every { movieGenresViewModelMock.genreLiveData } returns genreLiveData
        every { TestViewModelModule.viewModelFactory.create<MovieGenresViewModel>(any()) } returns movieGenresViewModelMock

        activityRule.launchActivity(null)

        navHostController = TestNavHostController(activityRule.activity)
        navHostController.setGraph(R.navigation.nav_graph)

        scenario = launchFragmentInContainer {
            genreListFragment = GenreListFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment's view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navHostController)
                    }
                }
            }
            genreListFragment
        }
    }

    @Test
    fun setMovieGenreListTest() {
        val expectedList = (getGenreMockList() as NewValue).result

        scenario.onFragment {
            _genreLiveData.value = getGenreMockList()
        }

        assertTrue(genreLiveData.value is NewValue)
        checkViewIsNotDisplayed(genreListFragment.genreListProgressBar.id)
        checkRecyclerViewItemCount(genreListFragment.genreListRecyclerView.id, expectedList.size)
        expectedList.forEachIndexed { index, element ->
            checkThatRecyclerViewItemHasText(
                genreListFragment.genreListRecyclerView.id,
                index,
                element.name
            )
        }
    }

    @Test
    fun onGenreClickedTest() {
        scenario.onFragment {
            _genreLiveData.value = getGenreMockList()
        }

        performRecyclerViewItemClick(genreListFragment.genreListRecyclerView.id, 0)

        assertEquals(navHostController.currentDestination?.id, R.id.movieListFragment)
    }

    @Test
    fun onLoadingMustShowLoadingProgressBarTest() {
        scenario.onFragment {
            it.onLoading()
        }

        checkViewIsDisplayed(genreListFragment.genreListProgressBar.id)
    }

    private fun getGenreMockList(): UiStatus<List<MovieGenreDetailModel>, ErrorModel> =
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