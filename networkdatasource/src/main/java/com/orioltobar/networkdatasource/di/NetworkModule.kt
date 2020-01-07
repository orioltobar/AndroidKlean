package com.orioltobar.networkdatasource.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orioltobar.networkdatasource.interceptors.UrlParamInterceptor
import com.orioltobar.networkdatasource.providers.NetworkProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
    private val baseUrlOverride: String? = null,
    private val isDebug: Boolean? = null
) {

    @Provides
    @BaseUrl
    fun provideBaseUrl() = baseUrlOverride ?: "https://api.themoviedb.org"

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
        @BaseUrl url: String,
        gsonConverterFactory:
        GsonConverterFactory
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(gsonConverterFactory)

    @Provides
    @Singleton
    fun provideHttpBuilder(
        networkProvider: NetworkProvider
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            addInterceptor(UrlParamInterceptor(networkProvider))
            addInterceptor(HttpLoggingInterceptor().apply {
                // TODO: Add isDebug variable to swith betwenn Level.BODY and Level.NONE
                level = HttpLoggingInterceptor.Level.BODY
            })
            readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideNetworkProvider(): NetworkProvider = object : NetworkProvider {
        override val apiKey: String
            get() = "96ef4725a812eee12b2943a66e535799"
        override val language: String
            get() = Locale.getDefault().language
    }

    companion object {
        const val RETROFIT_TIMEOUT = 60L
    }
}