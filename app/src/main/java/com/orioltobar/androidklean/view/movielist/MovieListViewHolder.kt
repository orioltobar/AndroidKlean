package com.orioltobar.androidklean.view.movielist

import android.view.ViewGroup
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseViewHolder
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListViewHolder(parent: ViewGroup) :
    BaseViewHolder<MovieModel>(parent, R.layout.movie_list_item) {

    override fun update(model: MovieModel) {
        itemView.movieViewHolderTitle.text = model.tittle
        itemView.movieViewHolderVotes.text = model.voteAverage.toString()
        itemView.movieViewHolderCountry.text = model.originalLanguage
        itemView.movieViewHolderSubtitle.text = model.originalTitle
        itemView.movieViewHolderGenre.text = model.genres?.genre?.let {
            "${it.getOrNull(0)?.name ?: ""} ${it.getOrNull(1)?.name ?: ""}"
        } ?: ""
        itemView.movieViewHolderDate.text = model.releaseDate
    }
}