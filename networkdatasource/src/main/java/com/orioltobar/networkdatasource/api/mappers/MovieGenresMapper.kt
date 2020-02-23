package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.domain.models.movie.MovieGenresModel
import com.orioltobar.networkdatasource.api.models.MovieGenresApiModel
import javax.inject.Inject

class MovieGenresMapper @Inject constructor(private val detailMapper: MovieGenreDetailMapper) :
    Mapper<MovieGenresApiModel, MovieGenresModel> {

    override fun map(from: MovieGenresApiModel?): MovieGenresModel = MovieGenresModel(
        from?.genre?.map(detailMapper::map) ?: emptyList()
    )
}

