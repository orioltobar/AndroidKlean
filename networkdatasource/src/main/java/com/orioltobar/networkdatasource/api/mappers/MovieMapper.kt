package com.orioltobar.networkdatasource.api.mappers

import com.orioltobar.commons.Mapper
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.networkdatasource.api.models.MovieApiModel
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<MovieApiModel, MovieModel> {

    override fun map(from: MovieApiModel?): MovieModel = MovieModel(
        from?.id ?: -1L,
        from?.originalTitle ?: "",
        from?.title ?: "",
        from?.popularity ?: 0.0F,
        from?.voteCount ?: 0L,
        from?.video ?: false,
        from?.frontImageUrl ?: "",
        from?.adult ?: false,
        from?.backImageUrl ?: "",
        from?.originalLanguage ?: "",
        from?.genreIds ?: emptyList(),
        from?.voteAverage ?: 0.0F,
        from?.overview ?: "",
        from?.releaseDate ?: ""
    )
}