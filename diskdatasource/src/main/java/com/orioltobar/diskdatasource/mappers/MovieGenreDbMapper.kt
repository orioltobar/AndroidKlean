package com.orioltobar.diskdatasource.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.diskdatasource.models.MovieGenreDbModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import javax.inject.Inject


class MovieGenreDbMapper @Inject constructor() : Mapper<MovieGenreDbModel, MovieGenreDetailModel> {

    override fun map(from: MovieGenreDbModel?): MovieGenreDetailModel =
        MovieGenreDetailModel(
            from?.id ?: -1,
            from?.name ?: ""
        )

    fun mapToDbModel(from: MovieGenreDetailModel): MovieGenreDbModel = MovieGenreDbModel(
        from.id,
        from.name
    )
}