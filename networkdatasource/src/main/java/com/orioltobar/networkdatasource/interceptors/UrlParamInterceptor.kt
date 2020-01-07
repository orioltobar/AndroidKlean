package com.orioltobar.networkdatasource.interceptors

import com.orioltobar.networkdatasource.providers.NetworkProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UrlParamInterceptor @Inject constructor(
    private val networkProvider: NetworkProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url().newBuilder().apply {
            addQueryParameter(API_KEY, networkProvider.apiKey)
            addQueryParameter(LANGUAGE, networkProvider.language)
        }
        val newRequest = request.newBuilder().url(newUrl.build()).build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
    }
}