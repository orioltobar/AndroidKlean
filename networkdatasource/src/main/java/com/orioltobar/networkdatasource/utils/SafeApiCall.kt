package com.orioltobar.networkdatasource.utils

import com.orioltobar.commons.Failure
import com.orioltobar.commons.Response
import com.orioltobar.commons.Success
import com.orioltobar.commons.error.ApiError
import com.orioltobar.commons.error.ErrorModel

/**
 * This function is used to wrap a [call] to an API in a safe way. The result is expressed as a
 * Coroutine Response which is a sealed class that contains the result of the operation.
 */
suspend fun <T> safeApiCall(
    call: suspend () -> T
): Response<ErrorModel, T> =
    try {
        val response = call()
        if (response == null) {
            Failure(ErrorModel("", ApiError.RequestError))
        } else {
            Success(response) as Response<Nothing, T>
        }
    } catch (exception: Exception) {
        Failure(ErrorModel(exception.message ?: "", ApiError.RequestError))
    }