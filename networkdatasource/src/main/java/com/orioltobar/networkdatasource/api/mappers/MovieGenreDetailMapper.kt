package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.networkdatasource.api.models.MovieGenreDetailApiModel
import javax.inject.Inject

class MovieGenreDetailMapper @Inject constructor() :
    Mapper<MovieGenreDetailApiModel, MovieGenreDetailModel> {

    override fun map(from: MovieGenreDetailApiModel?): MovieGenreDetailModel =
        MovieGenreDetailModel(
            from?.id ?: -1,
            from?.name ?: ""
        )
}