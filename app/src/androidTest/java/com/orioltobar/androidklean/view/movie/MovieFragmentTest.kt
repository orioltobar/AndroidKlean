package com.orioltobar.androidklean.view.movie

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.TestHelper.checkIsEmulator
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.base.MockActivity
import com.orioltobar.androidklean.di.TestViewModelModule
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.NewValue
import com.orioltobar.features.UiStatus
import com.orioltobar.features.viewmodel.MovieViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieFragmentTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var movieFragment: MovieFragment

    @MockK
    private lateinit var movieViewModelMock: MovieViewModel

    @get:Rule
    val activityRule =
        object : ActivityTestRule<MockActivity>(MockActivity::class.java, false, false) {
            override fun afterActivityLaunched() {
                super.afterActivityLaunched()
                runOnUiThread {
                    movieFragment = MovieFragment()
                    val args = Bundle().apply {
                        putLong("id", 15)
                    }
                    movieFragment.arguments = args
                    activity.setFragment(movieFragment)
                }
            }
        }

    private val _movieDataStream = MutableLiveData<UiStatus<MovieModel, ErrorModel>>()
    private val movieDataStream: LiveData<UiStatus<MovieModel, ErrorModel>>
        get() = _movieDataStream

    @Before
    fun setup() {
        every { movieViewModelMock.movieDataStream } returns movieDataStream
        every { TestViewModelModule.viewModelFactory.create<MovieViewModel>(any()) } returns movieViewModelMock

        activityRule.launchActivity(null)
    }

    @Test
    fun setMovieInfoTest() {
        with(activityRule.activity) {
            runOnUiThread {
                _movieDataStream.value = getMockResponse()
            }

            // TODO: Awful but this sleep ensures that Glide loads the image.
            Thread.sleep(500)

            checkTextIsDisplayed("Title Test")
            checkTextIsDisplayed("1978")
            checkTextIsDisplayed("Rate: 5.0")
            checkTextIsDisplayed("overview")
            // TODO: Glide is not loading the image in the emulator, only in a physical device. Check.
            if (!checkIsEmulator()) {
                checkViewIsDisplayed(R.id.movieFragmentImage)
            }

            assertTrue(_movieDataStream.value is NewValue)
        }
    }

    private fun getMockResponse(): UiStatus<MovieModel, ErrorModel> {
        val result = MovieModel(
            1L,
            "Original TitleTest",
            "Title Test",
            1.0F,
            1,
            true,
            "https://image.tmdb.org/t/p/w300/6sjMsBcIuqU44GpG5tL33KUFOQR.jpg",
            false,
            "/6sjMsBcIuqU44GpG5tL33KUFOQR.jpg",
            "en",
            listOf(12, 34),
            1,
            5.0F,
            "overview",
            "1978"
        )
        return NewValue(result)
    }
}