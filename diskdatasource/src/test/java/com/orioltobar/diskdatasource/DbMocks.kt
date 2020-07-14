package com.orioltobar.diskdatasource

import com.orioltobar.diskdatasource.models.MovieDbModel
import com.orioltobar.diskdatasource.models.MovieGenreDbModel

object DbMocks {

    fun getDbModel(id: Long = 1L, genreList: List<Int> = listOf(12, 34)) = MovieDbModel(
        id, "Test",
        "Test", 1.0F, 1, true,
        "/front", false, "/back",
        "en", genreList, 5.0F,
        "overview", "1978", 12345L
    )

    fun getDbGenreModel() = MovieGenreDbModel(12, "Action", "", 12345L)
}