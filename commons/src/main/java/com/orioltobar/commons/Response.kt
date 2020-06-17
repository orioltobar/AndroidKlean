package com.orioltobar.commons

import com.orioltobar.commons.error.ErrorModel

sealed class Response<out E, out T>

class Success<T>(val result: T) : Response<Nothing, T>()
class Failure(val error: ErrorModel) : Response<ErrorModel, Nothing>()

/**
 * Then response of [T] is mapped using a [lambda] to return a new CoroutineResponse type [V].
 */
inline fun <T, V> Response<ErrorModel, T>.mapResponse(lambda: (T) -> V): Response<ErrorModel, V> {
    return when (this) {
        is Success -> {
            Success(lambda(this.result))
        }
        is Failure -> {
            Failure(this.error)
        }
    }
}

/**
 * Either uses [onSuccess] to define a success callback if the result is Success. [onFailure]
 * defines the action to take if the call failed.
 *
 * @return The result of the took action.
 */
inline fun <T> Response<ErrorModel, T>.either(
    onSuccess: (T) -> Unit,
    onFailure: (ErrorModel) -> Unit
): Unit =
    when (this) {
        is Success -> {
            onSuccess.invoke(this.result)
        }
        is Failure -> {
            onFailure.invoke(this.error)
        }
    }

/**
 * Returns the result if Success, null elsewhere.
 */
fun <T> Response<ErrorModel, T>.valueOrNull(): T? =
    when (this) {
        is Success -> {
            this.result
        }
        else -> {
            null
        }
    }
