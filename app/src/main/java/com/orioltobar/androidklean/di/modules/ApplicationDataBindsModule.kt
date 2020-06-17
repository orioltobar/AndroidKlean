package com.orioltobar.androidklean.di.modules

import com.orioltobar.data.datasources.DbDataSource
import com.orioltobar.data.datasources.NetworkDataSource
import com.orioltobar.data.repositories.MovieRepositoryImpl
import com.orioltobar.diskdatasource.data.MovieDataBaseImpl
import com.orioltobar.domain.repositories.MovieRepository
import com.orioltobar.networkdatasource.api.data.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Module that binds dependencies for repositories and datasources
 */
@InstallIn(ApplicationComponent::class)
@Module
interface ApplicationDataBindsModule {

    @Binds
    @Singleton
    fun bindsMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun bindsMovieDataSource(dataSource: MovieDataSourceImpl): NetworkDataSource

    @Binds
    @Singleton
    fun provideMovieDataBaseDataSource(dataSource: MovieDataBaseImpl): DbDataSource
}