package com.orioltobar.androidklean.di

import com.orioltobar.androidklean.App
import com.orioltobar.androidklean.di.modules.AppModule
import com.orioltobar.androidklean.di.modules.DataBinderModule
import com.orioltobar.androidklean.di.modules.MainActivityModule
import com.orioltobar.networkdatasource.di.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        NetworkModule::class,
        DataBinderModule::class]
)
interface AppComponent : AndroidInjector<App>