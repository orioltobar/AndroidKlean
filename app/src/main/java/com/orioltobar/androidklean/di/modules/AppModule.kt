package com.orioltobar.androidklean.di.modules

import android.content.Context
import com.orioltobar.androidklean.App
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Named(NAME_BASE_URL)
    fun provideTextString(): String = "Test Dagger 2"

    companion object {
        const val NAME_BASE_URL = "NAME_BASE_URL"
    }
}