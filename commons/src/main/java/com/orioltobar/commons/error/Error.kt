package com.orioltobar.commons.error

interface Error

class GenericError: Error

sealed class ApiError : Error {
    object RequestError: ApiError()
}

sealed class DbError: Error {
    object QueryError: DbError()
    object CacheError: DbError()
}