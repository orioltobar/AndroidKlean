package com.orioltobar.androidklean.di

import androidx.appcompat.app.AppCompatActivity
import com.orioltobar.androidklean.App
import com.orioltobar.androidklean.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: AppCompatActivity)
}