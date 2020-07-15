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

        if (networkProvider.apiKey.isBlank()) {
            retryApiKeyRetrieve()
        }
        val newUrl = request.url().newBuilder().apply {
            addQueryParameter(API_KEY, networkProvider.apiKey)
            addQueryParameter(LANGUAGE, networkProvider.language)
        }
        val newRequest = request.newBuilder().url(newUrl.build()).build()
        return chain.proceed(newRequest)
    }

    /**
     * System to wait for Api key in case Firebase query is in process. Max time is
     * API_SLEEP_TIME * MAX_API_KEY_RETRY.
     */
    private fun retryApiKeyRetrieve() {
        var tries = 0
        while(tries < MAX_API_KEY_RETRY) {
            // The interceptor is always called in an IO thread, UI never gets blocked with this sleep.
            Thread.sleep(API_SLEEP_TIME)
            if (networkProvider.apiKey.isNotBlank()) break
            tries++
        }
    }

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val MAX_API_KEY_RETRY = 6
        private const val API_SLEEP_TIME = 500L
    }
}