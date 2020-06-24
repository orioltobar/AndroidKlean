package com.orioltobar.androidklean.view.movie

import android.os.Bundle
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.di.launchFragmentInHiltContainer
import com.orioltobar.commons.Success
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieUseCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MovieFragmentTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @MockK
    lateinit var getMovieUseCase: GetMovieUseCase

    @Test
    fun setMovieInfoTest() {
        coEvery { getMovieUseCase.invoke(any()) } returns Success(getMockResponse())

        launchFragmentInHiltContainer<MovieFragment>(args = Bundle().apply { putLong("id", -1) })

        checkTextIsDisplayed("Title Test")
        checkTextIsDisplayed("1978")
        checkTextIsDisplayed("Rate: 5.0")
        checkTextIsDisplayed("overview")

        Thread.sleep(500)

        checkViewIsDisplayed(R.id.movieFragmentImage)
    }

    private fun getMockResponse() =
        MovieModel(
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
}