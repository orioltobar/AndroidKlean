package com.orioltobar.commons

sealed class Response<out T, out E>

class Success<T>(val result: T) : Response<T, Nothing>()
class Failure<E>(val error: E) : Response<Nothing, E>()

/**
 * Then response of [T] is mapped using a [lambda] to return a new CoroutineResponse type [V].
 */
inline fun <T, V, E> Response<T, E>.mapResponse(lambda: (T) -> V): Response<V, E> {
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
inline fun <T, V, E> Response<T, E>.either(
    onSuccess: (T) -> V,
    onFailure: (E) -> V
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
fun <T, E> Response<T, E>.valueOrNull(): T? =
    when (this) {
        is Success -> {
            this.result
        }
        else -> {
            null
        }
    }
