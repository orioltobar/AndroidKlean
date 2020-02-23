package com.orioltobar.androidklean.di.modules

import com.orioltobar.androidklean.view.movie.MovieFragment
import com.orioltobar.androidklean.view.movielist.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    internal abstract fun bindMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    internal abstract fun bindMovieFragment(): MovieFragment
}