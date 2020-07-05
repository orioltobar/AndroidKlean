package com.orioltobar.androidklean.view.genrelist

import android.graphics.Color
import android.view.ViewGroup
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseViewHolder
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import kotlinx.android.synthetic.main.genre_list_item.view.*

class GenreListViewHolder(parent: ViewGroup) :
    BaseViewHolder<MovieGenreDetailModel>(parent, R.layout.genre_list_item) {

    override fun update(model: MovieGenreDetailModel) {
        val baseColor = Color.WHITE
        val red = (Color.red(baseColor) + (0..255).random()) / 2
        val green = (Color.green(baseColor) + (0..255).random()) / 2
        val blue = (Color.blue(baseColor) + (0..255).random()) / 2

        val textColor =
            if ((red * 0.299 + green * 0.587 + blue * 0.114) > 186) Color.BLACK else Color.WHITE
        itemView.genreListItemText.setTextColor(textColor)
        itemView.genreListItemText.text = model.name

        val randomColor = Color.rgb(red, green, blue)
        itemView.setBackgroundColor(randomColor)
    }
}