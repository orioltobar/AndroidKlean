package com.orioltobar.androidklean.di

import androidx.lifecycle.ViewModel
import com.orioltobar.features.viewmodel.MovieGenresViewModel
import com.orioltobar.features.viewmodel.MovieListViewModel
import com.orioltobar.features.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    internal abstract fun movieListViewModel(viewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieGenresViewModel::class)
    internal abstract fun movieGenresViewModel(viewModel: MovieGenresViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun movieViewModel(viewModel: MovieViewModel): ViewModel
}