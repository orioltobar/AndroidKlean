package com.orioltobar.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.usecases.GetGenresListUseCase
import com.orioltobar.features.CoroutineTestRule
import com.orioltobar.features.NewValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MovieGenresViewModelTest {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: TestRule = CoroutineTestRule()

    @MockK
    private lateinit var getGenresListUseCaseMock: GetGenresListUseCase


    private lateinit var movieGenresViewModel: MovieGenresViewModel

    @Before
    fun setUp() {
        coEvery { getGenresListUseCaseMock.execute() } returns getMockUseCaseResponse()
        movieGenresViewModel = MovieGenresViewModel(getGenresListUseCaseMock)
    }

    @Test
    fun `viewModel emits the value when useCase returns the result`() = runBlockingTest {
        Assert.assertTrue(movieGenresViewModel.genreLiveData.value is NewValue)
    }

    private fun getMockUseCaseResponse(): Response<List<MovieGenreDetailModel>, ErrorModel> =
        Success(
            mockk()
        )
}