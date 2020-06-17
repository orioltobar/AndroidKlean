package com.orioltobar.networkdatasource.api.models

import com.squareup.moshi.Json

data class MovieApiModel(
    @field:Json(name = "id")
    val id: Long? = null,

    @field:Json(name = "original_title")
    val originalTitle: String? = null,

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "popularity")
    val popularity: Float? = null,

    @field:Json(name = "vote_count")
    val voteCount: Long? = null,

    @field:Json(name = "video")
    val video: Boolean? = null,

    @field:Json(name = "poster_path")
    val frontImageUrl: String? = null,

    @field:Json(name = "adult")
    val adult: Boolean? = null,

    @field:Json(name = "backdrop_path")
    val backImageUrl: String? = null,

    @field:Json(name = "original_language")
    val originalLanguage: String? = null,

    @field:Json(name = "genre_ids")
    val genreIds: List<Int?>? = null,

    @field:Json(name = "vote_average")
    val voteAverage: Float? = null,

    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "release_date")
    val releaseDate: String? = null
)