package com.orioltobar.features.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.usecases.GetGenresListUseCase
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieGenresViewModel @ViewModelInject constructor(
    private val getGenresListUseCase: GetGenresListUseCase
) : BaseViewModel<List<MovieGenreDetailModel>>() {

    private val _genreLiveData =
        MutableLiveData<UiStatus<ErrorModel, List<MovieGenreDetailModel>>>()
    val genreLiveData: LiveData<UiStatus<ErrorModel, List<MovieGenreDetailModel>>>
        get() = _genreLiveData

    init {
        viewModelScope.launch {
            _genreLiveData.value = emitLoadingState()
            val request = getGenresListUseCase()
            _genreLiveData.value = processModel(request)
        }
    }
}