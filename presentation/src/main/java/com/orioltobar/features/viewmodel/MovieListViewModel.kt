package com.orioltobar.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieListUseCase
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieListUseCase: GetMovieListUseCase
) : BaseViewModel<MovieModel>() {

    private val _movieListDataStream = MutableLiveData<UiStatus<List<MovieModel>, ErrorModel>>()
    val movieListDataStream: LiveData<UiStatus<List<MovieModel>, ErrorModel>>
        get() = _movieListDataStream

    init {
        viewModelScope.launch {
            _movieListDataStream.value = emitLoadingState()
            val request = movieListUseCase.execute(1)
            _movieListDataStream.value = processModel(request)
        }
    }
}