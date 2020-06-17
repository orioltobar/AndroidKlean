package com.orioltobar.androidklean.base

import androidx.fragment.app.Fragment
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.features.Error
import com.orioltobar.features.Loading
import com.orioltobar.features.NewValue
import com.orioltobar.features.UiStatus

abstract class BaseFragment : Fragment() {

    fun <T> handleUiStates(
        status: UiStatus<ErrorModel, T>,
        success: (T) -> Unit
    ) =
        when (status) {
            is Loading -> {
                onLoading()
            }
            is NewValue -> success.invoke(status.result)
            is Error -> onError(status.error)
        }

    abstract fun onError(error: ErrorModel)

    abstract fun onLoading()
}