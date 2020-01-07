package com.orioltobar.androidklean.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [ApplicationDataBindsModule::class])
object AppModule {

    const val NAME_BASE_URL = "NAME_BASE_URL"

    @Provides
    @Named(NAME_BASE_URL)
    fun provideTextString(): String = "Test Dagger 2"
}