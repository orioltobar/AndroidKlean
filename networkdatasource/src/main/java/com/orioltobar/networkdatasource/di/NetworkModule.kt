package com.orioltobar.networkdatasource.di

import com.orioltobar.commons.Constants.API_KEY
import com.orioltobar.commons.Constants.RETROFIT_TIMEOUT
import com.orioltobar.commons.Constants.USER_LANGUAGE
import com.orioltobar.networkdatasource.api.data.MovieService
import com.orioltobar.networkdatasource.interceptors.UrlParamInterceptor
import com.orioltobar.networkdatasource.providers.NetworkProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

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
            readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        }

    @Provides
    @Singleton
    fun provideNetworkProvider(
        @Named(API_KEY) apiKey: String,
        @Named(USER_LANGUAGE) language: String
    ): NetworkProvider = object : NetworkProvider {
        override val apiKey: String
            get() = apiKey
        override val language: String
            get() = language
    }
}

@Module
object NetworkServicesModule {

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)
}