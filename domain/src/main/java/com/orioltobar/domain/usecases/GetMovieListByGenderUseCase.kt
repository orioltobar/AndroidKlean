package com.orioltobar.domain.usecases

import com.orioltobar.domain.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieListByGenderUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun execute(genreId: Int) = withContext(Dispatchers.IO) {
        repository.getMovieListByGender(genreId)
    }
}