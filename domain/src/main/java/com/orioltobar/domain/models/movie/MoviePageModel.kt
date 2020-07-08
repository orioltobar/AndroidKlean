package com.orioltobar.domain.models.movie

data class MoviePageModel(
    val id: Long,
    val totalResults: Long,
    val totalPages: Long,
    val movies: List<MovieModel>
)