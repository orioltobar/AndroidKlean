package com.orioltobar.features.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.Constants
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieListByGenreUseCase
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieListViewModel @ViewModelInject constructor(
    private val movieListUseCase: GetMovieListByGenreUseCase
) : BaseViewModel<MovieModel>() {

    private val _movieListDataStream = MutableLiveData<UiStatus<ErrorModel, List<MovieModel>>>()
    val movieListDataStream: LiveData<UiStatus<ErrorModel, List<MovieModel>>>
        get() = _movieListDataStream

    fun getMovieListFromGenre(genreId: Int) {
        val safeValue = if (genreId == -1) Constants.DEFAULT_GENRE_ID else genreId
        viewModelScope.launch {
            _movieListDataStream.value = emitLoadingState()
            val request = movieListUseCase(safeValue)
            _movieListDataStream.value = processModel(request)
        }
    }
}