package com.orioltobar.androidklean.di.modules

import com.orioltobar.commons.Constants.API_KEY
import com.orioltobar.commons.Constants.USER_LANGUAGE
import com.orioltobar.networkdatasource.di.BaseUrl
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Named

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
}