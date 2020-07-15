package com.orioltobar.androidklean.di.modules

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.orioltobar.commons.Constants
import com.orioltobar.androidklean.di.BaseUrl
import com.orioltobar.networkdatasource.interceptors.UrlParamInterceptor
import com.orioltobar.networkdatasource.providers.NetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

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
            readTimeout(Constants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(Constants.RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        }

    @Provides
    @Singleton
    fun provideNetworkProvider(
        firebaseRemoteConfig: FirebaseRemoteConfig,
        @Named(Constants.USER_LANGUAGE) language: String
    ): NetworkProvider = object : NetworkProvider {
        override val apiKey: String
            get() = firebaseRemoteConfig.getString(Constants.API_KEY)
        override val language: String
            get() = language
    }
}