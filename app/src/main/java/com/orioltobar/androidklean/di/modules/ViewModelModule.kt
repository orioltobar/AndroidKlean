package com.orioltobar.androidklean.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orioltobar.androidklean.di.ViewModelFactory
import com.orioltobar.androidklean.di.ViewModelKey
import com.orioltobar.features.viewmodel.MovieGenresViewModel
import com.orioltobar.features.viewmodel.MovieListViewModel
import com.orioltobar.features.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun movieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    internal abstract fun movieListViewModel(viewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieGenresViewModel::class)
    internal abstract fun movieGenresViewModel(viewModel: MovieGenresViewModel): ViewModel
}