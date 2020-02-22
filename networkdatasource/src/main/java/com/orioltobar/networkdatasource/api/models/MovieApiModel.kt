package com.orioltobar.networkdatasource.api.models

import com.google.gson.annotations.SerializedName

data class MovieApiModel(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("vote_count")
    val voteCount: Long,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("poster_path")
    val frontImageUrl: String,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backImageUrl: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String
)