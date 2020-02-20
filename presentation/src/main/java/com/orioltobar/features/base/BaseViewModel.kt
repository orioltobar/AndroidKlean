package com.orioltobar.features.base

import androidx.lifecycle.ViewModel
import com.orioltobar.commons.Response

//TODO: Finish.
abstract class BaseViewModel<T> : ViewModel() {

    abstract fun processModel(model: Response<T>)
}