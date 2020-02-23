package com.orioltobar.networkdatasource.interceptors

import com.orioltobar.networkdatasource.providers.NetworkProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verifySequence
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test

class UrlParamInterceptorTest {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }


    @io.mockk.impl.annotations.MockK
    private lateinit var networkProviderMock: NetworkProvider

    @io.mockk.impl.annotations.MockK
    private lateinit var chain: Interceptor.Chain
    @io.mockk.impl.annotations.MockK
    private lateinit var request: Request
    @io.mockk.impl.annotations.MockK
    private lateinit var httpUrl: HttpUrl
    @io.mockk.impl.annotations.MockK
    private lateinit var httpUrlBuilder: HttpUrl.Builder
    @io.mockk.impl.annotations.MockK
    private lateinit var requestBuilder: Request.Builder
    @io.mockk.impl.annotations.MockK
    private lateinit var response: Response

    private val baseUrlInterceptor by lazy {
        UrlParamInterceptor(networkProviderMock)
    }

    @Before
    fun setup() {
        every { chain.request() } returns request
        every { request.url() } returns httpUrl
        every { httpUrl.pathSegments() } returns emptyList()
        every { httpUrl.newBuilder() } returns httpUrlBuilder
        every { httpUrlBuilder.scheme(any()) } returns httpUrlBuilder
        every { httpUrlBuilder.host(any()) } returns httpUrlBuilder
        every { httpUrlBuilder.setPathSegment(any(), any()) } returns httpUrlBuilder
        every { httpUrlBuilder.addPathSegment(any()) } returns httpUrlBuilder
        every { httpUrlBuilder.build() } returns httpUrl

        every { request.newBuilder() } returns requestBuilder
        every { requestBuilder.url(httpUrl) } returns requestBuilder
        every { requestBuilder.build() } returns request

        every { chain.proceed(request) } returns response
    }

    @Test
    fun `URL is well formed when params are not empty`() {
        every { networkProviderMock.apiKey } returns "test"
        every { networkProviderMock.language } returns "en"

        baseUrlInterceptor.intercept(chain)

        verifySequence {
            httpUrlBuilder.addQueryParameter(any(), "test")
            httpUrlBuilder.addQueryParameter(any(), "en")
            httpUrlBuilder.build()
        }
    }

    @Test
    fun `URL is well formed when params are empty`() {
        every { networkProviderMock.apiKey } returns ""
        every { networkProviderMock.language } returns ""

        baseUrlInterceptor.intercept(chain)

        verifySequence {
            httpUrlBuilder.addQueryParameter(any(), "")
            httpUrlBuilder.addQueryParameter(any(), "")
            httpUrlBuilder.build()
        }
    }
}