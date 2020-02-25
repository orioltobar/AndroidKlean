package com.orioltobar.networkdatasource.api.models

import com.google.gson.annotations.SerializedName

data class MovieGenresApiModel(
    @SerializedName("genres")
    val genre: List<MovieGenreDetailApiModel?>? = null
)

data class MovieGenreDetailApiModel(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)