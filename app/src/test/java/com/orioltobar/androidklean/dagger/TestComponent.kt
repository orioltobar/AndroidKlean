package com.orioltobar.androidklean.dagger

import android.content.Context
import com.orioltobar.androidklean.App
import com.orioltobar.androidklean.di.modules.ActivityBindingModule
import com.orioltobar.androidklean.di.modules.AppModule
import com.orioltobar.commons.Constants
import com.orioltobar.networkdatasource.di.BaseUrl
import com.orioltobar.networkdatasource.interceptors.UrlParamInterceptor
import com.orioltobar.networkdatasource.providers.NetworkProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        TestNetworkModule::class]
)

interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context, @BindsInstance @BaseUrl baseUrl: String): AppComponent
    }
}

@Module
object TestNetworkModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl() = "http://127.0.0.1:8080"

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit =
        retrofitBuilder.client(okHttpBuilder.build()).build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        @BaseUrl url: String
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())

    @Provides
    @Singleton
    fun provideHttpBuilder(
        networkProvider: NetworkProvider
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            addInterceptor(UrlParamInterceptor(networkProvider))
            addInterceptor(HttpLoggingInterceptor().apply {
                // TODO: Add isDebug variable to switch between Level.BODY and Level.NONE
                level = HttpLoggingInterceptor.Level.BODY
            })
            readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        }

    @Provides
    @Singleton
    fun provideNetworkProvider(
        @Named(Constants.API_KEY) apiKey: String,
        @Named(Constants.USER_LANGUAGE) language: String
    ): NetworkProvider = object : NetworkProvider {
        override val apiKey: String
            get() = apiKey
        override val language: String
            get() = language
    }

    private const val RETROFIT_TIMEOUT = 60L
}