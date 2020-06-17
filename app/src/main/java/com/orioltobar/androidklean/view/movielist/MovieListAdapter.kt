package com.orioltobar.androidklean.view.movielist

import android.view.ViewGroup
import com.orioltobar.androidklean.base.BaseAdapter
import com.orioltobar.domain.models.movie.MovieModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class MovieListAdapter @Inject constructor() : BaseAdapter<MovieModel, MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(parent)
}