package com.orioltobar.networkdatasource.api.models

import com.squareup.moshi.Json

data class MovieGenresApiModel(
    @field:Json(name = "genres")
    val genre: List<MovieGenreDetailApiModel?>? = null
)

data class MovieGenreDetailApiModel(
    @field:Json(name ="id")
    val id: Int? = null,

    @field:Json(name ="name")
    val name: String? = null
)