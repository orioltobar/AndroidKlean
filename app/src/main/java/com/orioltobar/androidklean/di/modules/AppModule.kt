package com.orioltobar.androidklean.di.modules

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.orioltobar.androidklean.di.BaseUrl
import com.orioltobar.commons.AppDispatchers
import com.orioltobar.commons.Constants.API_KEY
import com.orioltobar.commons.Constants.USER_LANGUAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Named(API_KEY)
    fun provideApiKey(): String = "96ef4725a812eee12b2943a66e535799"

    @Provides
    @Named(USER_LANGUAGE)
    fun provideLanguage(): String = Locale.getDefault().language

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://api.themoviedb.org"

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = object : AppDispatchers {
        override val main: CoroutineDispatcher = Dispatchers.Main
        override val io: CoroutineDispatcher = Dispatchers.IO
        override val default: CoroutineDispatcher = Dispatchers.Default
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    }

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.applicationContext.getSharedPreferences(FILE_NAME_STORE_MODE, MODE_PRIVATE)
}

private const val FILE_NAME_STORE_MODE: String = "CacheSharedPreferences"