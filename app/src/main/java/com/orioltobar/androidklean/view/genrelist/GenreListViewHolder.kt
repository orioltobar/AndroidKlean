package com.orioltobar.androidklean.view.genrelist

import android.view.ViewGroup
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseViewHolder
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import kotlinx.android.synthetic.main.genre_list_item.view.*

class GenreListViewHolder(parent: ViewGroup) :
    BaseViewHolder<MovieGenreDetailModel>(parent, R.layout.genre_list_item) {

    override fun update(model: MovieGenreDetailModel) {
        itemView.genreListItemText.text = model.name
    }
}