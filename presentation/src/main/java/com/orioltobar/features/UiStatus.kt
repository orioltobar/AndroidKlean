package com.orioltobar.features

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ErrorModel

/**
 * Defines the possibles status of a view.
 */
sealed class UiStatus<out E, out V>

object Loading : UiStatus<Nothing, Nothing>()
class NewValue<T>(val result: T) : UiStatus<Nothing, T>()
class Error<E>(val error: E) : UiStatus<E, Nothing>()

fun <E, T> processResponse(action: Response<E, T>) =
    when (action) {
        is Success -> NewValue(action.result)
        is Failure -> Error(action.error)
    }