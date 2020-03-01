package com.orioltobar.features.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orioltobar.commons.Success
import com.orioltobar.domain.usecases.GetMovieListByGenreUseCase
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
class MovieListViewModelTest {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: TestRule = CoroutineTestRule()

    @MockK
    private lateinit var movieListUseCaseMock: GetMovieListByGenreUseCase

    private val movieListViewModel: MovieListViewModel by lazy {
        MovieListViewModel(movieListUseCaseMock)
    }

    @Before
    fun setUp() {
        coEvery { movieListUseCaseMock.execute(any()) } returns Success(mockk())
    }

    @Test
    fun `viewModel emits the value when useCase returns the result`() = runBlockingTest {
        movieListViewModel.getMovieListFromGenre(12)

        Assert.assertTrue(movieListViewModel.movieListDataStream.value is NewValue)
    }
}