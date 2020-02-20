package com.orioltobar.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orioltobar.commons.Response
import com.orioltobar.commons.either
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.usecases.GetMovieUseCase
import com.orioltobar.domain.usecases.GetMovieUseCaseFlow
import com.orioltobar.features.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieUseCaseFlow: GetMovieUseCaseFlow
) :
    BaseViewModel<MovieModel>() {

    private val _movieUiModel = MutableLiveData<MovieModel>()
    val movieUiModel: LiveData<MovieModel>
        get() = _movieUiModel

    private val _movieUiModelFlow =
        getMovieUseCaseFlow.execute().flowOn(Dispatchers.IO).asLiveData(viewModelScope.coroutineContext)
    val movieModelFlow: LiveData<Response<MovieModel>>
        get() = _movieUiModelFlow

    fun execute(id: Long = 550L) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = getMovieUseCase.execute(id)
            processModel(request)
        }
    }

    override fun processModel(model: Response<MovieModel>) {
        model.either(
            {
                viewModelScope.launch(Dispatchers.Main) {
                    _movieUiModel.value = it
                }
            }, {
                viewModelScope.launch(Dispatchers.Main) {
                    //TODO: TESTING only.
                    _movieUiModel.value = MovieModel(-1L, "error", "error")
                }
            }
        )
    }
}