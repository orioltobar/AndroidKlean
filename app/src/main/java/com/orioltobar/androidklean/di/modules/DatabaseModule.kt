package com.orioltobar.androidklean.di.modules

import android.app.Application
import com.orioltobar.diskdatasource.dao.MovieDao
import com.orioltobar.diskdatasource.dao.MovieGenreDao
import com.orioltobar.diskdatasource.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(application: Application): AppDataBase = AppDataBase.getInstance(application)

    @Provides
    @Singleton
    fun provideMovieDao(appDataBase: AppDataBase): MovieDao = appDataBase.movieDao()

    @Provides
    @Singleton
    fun provideMovieGenreDao(appDataBase: AppDataBase): MovieGenreDao = appDataBase.movieGenreDao()
}