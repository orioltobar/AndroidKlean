package com.orioltobar.androidklean

import com.orioltobar.androidklean.di.DaggerAppComponent
import com.orioltobar.androidklean.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
}