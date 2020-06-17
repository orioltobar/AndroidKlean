package com.orioltobar.networkdatasource.api.models

import com.squareup.moshi.Json

data class MovieListApiModel(
    @field:Json(name ="results")
    val movieList: List<MovieApiModel> = emptyList()
)