package com.orioltobar.androidklean.view.movielist

import android.view.ViewGroup
import com.orioltobar.androidklean.base.BaseAdapter
import com.orioltobar.domain.models.movie.MovieModel
import javax.inject.Inject

class MovieListAdapter @Inject constructor() : BaseAdapter<MovieModel, MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(parent)
}