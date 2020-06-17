package com.orioltobar.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.usecases.GetGenresListUseCase
import com.orioltobar.features.UiStatus
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieGenresViewModel @Inject constructor(
    private val getGenresListUseCase: GetGenresListUseCase
) : BaseViewModel<List<MovieGenreDetailModel>>() {

    private val _genreLiveData =
        MutableLiveData<UiStatus<List<MovieGenreDetailModel>, ErrorModel>>()
    val genreLiveData: LiveData<UiStatus<List<MovieGenreDetailModel>, ErrorModel>>
        get() = _genreLiveData

    init {
        viewModelScope.launch {
            emitLoadingState()
            val request = getGenresListUseCase()
            _genreLiveData.value = processModel(request)
        }
    }
}