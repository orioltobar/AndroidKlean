package com.orioltobar.networkdatasource.api.models

import com.squareup.moshi.Json

data class MoviePageApiModel(
    @field:Json(name = "page")
    val page: Long? = null,

    @field:Json(name = "total_results")
    val totalResults: Long? = null,

    @field:Json(name = "total_pages")
    val totalPages: Long? = null,

    @field:Json(name = "results")
    val movies: List<MovieApiModel>? = null
)