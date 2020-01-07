package com.orioltobar.networkdatasource.api.models

import com.google.gson.annotations.SerializedName

data class MovieApiModel(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("title")
    val title: String? = null
)