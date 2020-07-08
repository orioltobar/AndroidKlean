package com.orioltobar.features.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.commons.valueOrNull
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.usecases.GetGenresListUseCase
import com.orioltobar.domain.usecases.GetMovieListByGenreUseCase
import com.orioltobar.domain.usecases.SaveMovieGenresUseCase
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.random.Random

class MovieGenresViewModel @ViewModelInject constructor(
    private val getGenresListUseCase: GetGenresListUseCase,
    private val getMovieListByGenreUseCase: GetMovieListByGenreUseCase,
    private val saveMovieGenresUseCase: SaveMovieGenresUseCase
) : BaseViewModel<List<MovieGenreDetailModel>>() {

    private val _genreLiveData =
        MutableLiveData<UiStatus<ErrorModel, List<MovieGenreDetailModel>>>()
    val genreLiveData: LiveData<UiStatus<ErrorModel, List<MovieGenreDetailModel>>>
        get() = _genreLiveData

    init {
        viewModelScope.launch {
            _genreLiveData.value = emitLoadingState()
            val request = getGenresListUseCase()
            val result = request.valueOrNull()?.let { Success(getGenreImageCover(it)) } ?: request
            _genreLiveData.value = processModel(result)
        }
    }

    private suspend fun getGenreImageCover(genres: List<MovieGenreDetailModel>) = supervisorScope {
        val genreWithCoverImages = mutableListOf<MovieGenreDetailModel>()
        val filteredList = genres.filter { it.coverUrl.isEmpty() }
        val request =
            filteredList.map { genre -> async { genre to getMovieListByGenreUseCase.invoke(genre.id) } }
        request.awaitAll().forEach { response ->
            val cover =
                response.second.valueOrNull()?.let { it[Random.nextInt(0, it.size)].frontImageUrl }
                    ?: ""
            genres.find { it.id == response.first.id }?.let { it.coverUrl = cover }
            genreWithCoverImages.add(
                MovieGenreDetailModel(
                    response.first.id,
                    response.first.name,
                    cover
                )
            )
        }
        genreWithCoverImages.takeIf { it.isNotEmpty() }?.let { saveMovieGenresUseCase(it.toList()) }
        genres
    }
}