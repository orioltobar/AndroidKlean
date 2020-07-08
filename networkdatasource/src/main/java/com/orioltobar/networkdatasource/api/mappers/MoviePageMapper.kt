package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.domain.models.movie.MoviePageModel
import com.orioltobar.networkdatasource.api.models.MoviePageApiModel
import javax.inject.Inject

class MoviePageMapper @Inject constructor(
    private val movieMapper: MovieMapper
) : Mapper<MoviePageApiModel, MoviePageModel> {

    override fun map(from: MoviePageApiModel?): MoviePageModel = MoviePageModel(
        from?.page ?: -1,
        from?.totalResults ?: -1,
        from?.totalPages ?: -1,
        from?.movies?.map(movieMapper::map) ?: emptyList()
    )
}