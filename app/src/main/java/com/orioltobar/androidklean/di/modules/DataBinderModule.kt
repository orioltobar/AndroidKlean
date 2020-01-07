package com.orioltobar.androidklean.di.modules

import com.orioltobar.data.datasources.MovieDataSource
import com.orioltobar.data.repositories.MovieRepositoryImpl
import com.orioltobar.domain.repositories.MovieRepository
import com.orioltobar.networkdatasource.api.data.MovieDataSourceImpl
import dagger.Binds
import dagger.Module

/**
 * Module that binds dependencies for repositories and datasources
 */
@Module
abstract class DataBinderModule {

    @Binds
    internal abstract fun bindsMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    internal abstract fun bindsMovieDataSource(datasource: MovieDataSourceImpl): MovieDataSource
}