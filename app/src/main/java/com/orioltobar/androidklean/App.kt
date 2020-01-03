package com.orioltobar.androidklean

import android.app.Application
import com.orioltobar.androidklean.di.AppComponent
import com.orioltobar.androidklean.di.DaggerAppComponent

class App: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}