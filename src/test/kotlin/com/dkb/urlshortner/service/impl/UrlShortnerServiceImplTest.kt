package com.dkb.urlshortner.service.impl

import com.dkb.urlshortner.connector.TinyUrlConnector
import com.dkb.urlshortner.entity.TinyUrlData
import com.dkb.urlshortner.error.ApiException
import com.dkb.urlshortner.service.UrlShortnerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito


class UrlShortnerServiceJTest {

    private val LONG_URL = "http://long-url-example.com/i-am-very-very-very-long-url"
    private var urlShortnerService: UrlShortnerService? = null
    private val tinyUrlConnector = Mockito.mock(TinyUrlConnector::class.java)

    @BeforeEach
    fun setup() {
        urlShortnerService = UrlShortnerServiceImpl(tinyUrlConnector)
    }

    @Test
    fun createShortUrl() {
        val tinyUrlData = TinyUrlData()
        tinyUrlData.tiny_url = "http://tiny.com/abc123"
        tinyUrlData.url = LONG_URL
        tinyUrlData.alias = "abc123"
        Mockito.`when`(tinyUrlConnector.generateTinyUrl(LONG_URL))
            .thenReturn(tinyUrlData)
        val actualUrlComponent = urlShortnerService!!.createShortUrl(LONG_URL)
        Assertions.assertEquals(tinyUrlData.url, actualUrlComponent!!.actualUrl)
        Assertions.assertEquals(tinyUrlData.tiny_url, actualUrlComponent!!.tinyUrl)
        Assertions.assertEquals(tinyUrlData.alias, actualUrlComponent!!.alias)
    }

    @Test
    fun createShortUrl_emptyUrlData() {
        Mockito.`when`(tinyUrlConnector.generateTinyUrl(LONG_URL))
            .thenReturn(null)
        Assertions.assertThrows(
            ApiException::class.java
        ) { urlShortnerService!!.createShortUrl(LONG_URL) }
    }
}