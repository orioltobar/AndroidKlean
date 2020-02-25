package com.orioltobar.diskdatasource

import com.orioltobar.diskdatasource.models.MovieDbModel
import com.orioltobar.diskdatasource.models.MovieGenreDbModel

object DbMocks {

    fun getDbModel(mainGenreId: Int = 12, id: Long = 1L) = MovieDbModel(
        id, mainGenreId, "Test",
        "Test", 1.0F, 1, true,
        "/front", false, "/back",
        "en", listOf(12, 34), 5.0F,
        "overview", "1978", 12345L
    )

    fun getDbGenreModel() = MovieGenreDbModel(12, "Action")
}