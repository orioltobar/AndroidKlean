package com.orioltobar.androidklean.di.modules

import com.orioltobar.commons.AppDispatchers
import com.orioltobar.commons.Constants.API_KEY
import com.orioltobar.commons.Constants.USER_LANGUAGE
import com.orioltobar.networkdatasource.di.BaseUrl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ApplicationDataBindsModule::class])
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
}