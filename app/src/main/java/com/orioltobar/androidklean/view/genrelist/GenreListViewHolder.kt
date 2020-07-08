package com.orioltobar.androidklean.view.genrelist

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseViewHolder
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import kotlinx.android.synthetic.main.genre_list_item.view.*

class GenreListViewHolder(parent: ViewGroup) :
    BaseViewHolder<MovieGenreDetailModel>(parent, R.layout.genre_list_item) {

    override fun update(model: MovieGenreDetailModel) {
        Glide.with(itemView.context)
            .load(model.coverUrl)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.genreListFragmentImage.alpha = 0.7F
                    return false
                }
            })
            .transform(CenterCrop(), RoundedCorners(30))
            .into(itemView.genreListFragmentImage)

        itemView.genreListItemText.text = model.name
    }
}