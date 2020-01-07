package com.orioltobar.commons

sealed class CoroutineResponse<out T>

class Success<T>(val result: T) : CoroutineResponse<T>()
class Failure(val error: Throwable?) : CoroutineResponse<Nothing>()

/**
 * Then response of [T] is mapped using a [lambda] to return a new CoroutineResponse type [V].
 */
inline fun <T, V> CoroutineResponse<T>.mapResponse(lambda: (T) -> V): CoroutineResponse<V> {
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
inline fun <T, V> CoroutineResponse<T>.either(
    onSuccess: (T) -> V,
    onFailure: (Throwable?) -> V
): V =
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
fun <T> CoroutineResponse<T>.valueOrNull(): T? =
    when (this) {
        is Success -> {
            this.result
        }
        else -> {
            null
        }
    }
