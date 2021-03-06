package com.orioltobar.androidklean.view.genrelist

import android.view.ViewGroup
import com.orioltobar.androidklean.base.BaseAdapter
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GenreListAdapter @Inject constructor() :
    BaseAdapter<MovieGenreDetailModel, GenreListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder =
        GenreListViewHolder(parent)
}