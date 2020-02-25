package com.orioltobar.features.viewmodel

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
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieListUseCase: GetMovieListByGenreUseCase
) : BaseViewModel<MovieModel>() {

    private val _movieListDataStream = MutableLiveData<UiStatus<List<MovieModel>, ErrorModel>>()
    val movieListDataStream: LiveData<UiStatus<List<MovieModel>, ErrorModel>>
        get() = _movieListDataStream

    fun getMovieListFromGenre(genreId: Int) {
        val safeValue = if (genreId == -1) Constants.DEFAULT_GENRE_ID else genreId
        viewModelScope.launch {
            _movieListDataStream.value = emitLoadingState()
            val request = movieListUseCase.execute(safeValue)
            _movieListDataStream.value = processModel(request)
        }
    }
}