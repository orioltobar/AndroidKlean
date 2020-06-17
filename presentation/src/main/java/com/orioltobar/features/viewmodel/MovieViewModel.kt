package com.orioltobar.features.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieUseCase
import com.orioltobar.domain.usecases.GetMovieUseCaseFlow
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieViewModel @ViewModelInject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieUseCaseFlow: GetMovieUseCaseFlow
) :
    BaseViewModel<MovieModel>() {

    private val _movieDataStream = MutableLiveData<UiStatus<ErrorModel, MovieModel>>()
    val movieDataStream: LiveData<UiStatus<ErrorModel, MovieModel>>
        get() = _movieDataStream

    private val _movieUiModelFlow =
        getMovieUseCaseFlow.execute()
            .asLiveData(viewModelScope.coroutineContext)
    val movieModelFlow: LiveData<Response<ErrorModel, MovieModel>>
        get() = _movieUiModelFlow

    fun execute(id: Long) {
        viewModelScope.launch {
            _movieDataStream.value = emitLoadingState()
            val request = getMovieUseCase(id)
            _movieDataStream.value = processModel(request)
        }
    }
}