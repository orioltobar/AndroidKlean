package com.orioltobar.commons

import com.orioltobar.commons.error.ErrorModel

/**
 * Single source of truth pattern. Database serves as SSOT in this case. Network calls are used
 * to store the values in the database.
 */
suspend fun <V> singleSourceOfTruth(
    dbDataSource: suspend () -> Response<ErrorModel, V>,
    networkDataSource: suspend () -> Response<ErrorModel, V>,
    dbCallback: suspend (V) -> Response<ErrorModel, V>
): Response<ErrorModel, V> =
    dbDataSource().let { response ->
        when (response) {
            is Success -> Success(response.result)
            is Failure -> {
                networkDataSource().let { apiResult ->
                    when (apiResult) {
                        is Success -> dbCallback(apiResult.result)
                        is Failure -> Failure(apiResult.error)
                    }
                }
            }
        }
    }