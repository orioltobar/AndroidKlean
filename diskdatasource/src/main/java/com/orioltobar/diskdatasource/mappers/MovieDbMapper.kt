package com.orioltobar.diskdatasource.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.diskdatasource.models.MovieDbModel
import com.orioltobar.domain.models.movie.MovieModel
import java.util.*
import javax.inject.Inject

class MovieDbMapper @Inject constructor(): Mapper<MovieDbModel, MovieModel> {

    override fun map(from: MovieDbModel?): MovieModel =
        MovieModel(
            from?.id ?: -1L,
            from?.originalTitle ?: "",
            from?.tittle ?: ""
        )

    fun mapToDbModel(from: MovieModel): MovieDbModel =
        MovieDbModel(
            from.id,
            from.originalTitle,
            from.tittle,
            Date().time
        )
}