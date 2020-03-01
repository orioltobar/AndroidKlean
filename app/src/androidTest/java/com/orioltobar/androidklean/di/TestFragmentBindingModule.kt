package com.orioltobar.androidklean.di

import com.orioltobar.androidklean.view.discover.DiscoverFragment
import com.orioltobar.androidklean.view.genrelist.GenreListFragment
import com.orioltobar.androidklean.view.movie.MovieFragment
import com.orioltobar.androidklean.view.movielist.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class TestFragmentBindingModule {

    @ContributesAndroidInjector
    internal abstract fun bindMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    internal abstract fun bindGenreListFragment(): GenreListFragment

    @ContributesAndroidInjector
    internal abstract fun bindMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    internal abstract fun bindDiscoverFragment(): DiscoverFragment
}
