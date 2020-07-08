package com.orioltobar.domain.models.movie

data class MovieGenresModel(
    val genre: List<MovieGenreDetailModel>
)

data class MovieGenreDetailModel(
    val id: Int,
    val name: String,
    var coverUrl: String
)