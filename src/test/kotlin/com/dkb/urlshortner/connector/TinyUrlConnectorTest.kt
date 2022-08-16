package com.dkb.urlshortner.connector

import com.dkb.urlshortner.entity.TinyUrlData
import com.dkb.urlshortner.entity.TinyUrlRequest
import com.dkb.urlshortner.entity.TinyUrlResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

internal class TinyUrlConnectorTest {

    private var tinyUrlConnector: TinyUrlConnector? = null
    private val restTemplate = Mockito.mock(RestTemplate::class.java, Mockito.RETURNS_DEEP_STUBS)

    @BeforeEach
    fun setup() {
        tinyUrlConnector = TinyUrlConnector(restTemplate)
    }

    @Test
    fun generateTinyUrl() {
        val tinyUrlRequest = TinyUrlRequest()
        tinyUrlRequest.url = "http://examplr.com/i-want-to-be-shorten/i-am-long"

        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer hYmrzfz1pntFdz38XlKuiTIyj5lpJpNxuNlzY7M8rUJRKMNoi1oCCh8xhwBa");
        val tinyUrlRequestHttpEntity = HttpEntity(tinyUrlRequest, headers)

        val expectedResponse = TinyUrlResponse()
        val tinyUrlData = TinyUrlData()
        tinyUrlData.tiny_url = "http://tiny.com/123abc"
        tinyUrlData.url = "http://longdomain.com/i-am-very-long-url/short-me"
        tinyUrlData.alias = "123abc"
        expectedResponse.data = tinyUrlData

        Mockito.`when`(
            restTemplate.exchange(
                anyString(), eq(HttpMethod.POST),
                any(HttpEntity::class.java), eq(
                    TinyUrlResponse::class.java
                )
            )
        ).thenReturn(ResponseEntity.ok(expectedResponse))

        val actualTinyUrlData: TinyUrlData? = tinyUrlConnector!!.generateTinyUrl("http://examplr.com/i-want-to-be-shorten/i-am-long")
        Assertions.assertNotNull(actualTinyUrlData);

        if(actualTinyUrlData != null) {
            Assertions.assertEquals(tinyUrlData.tiny_url, actualTinyUrlData.tiny_url)
            Assertions.assertEquals(tinyUrlData.url, actualTinyUrlData.url)
            Assertions.assertEquals(tinyUrlData.alias, actualTinyUrlData.alias)
        }

        Mockito.verify(restTemplate, times(1)).exchange(
            eq("https://api.tinyurl.com" + "/create"),
            eq(HttpMethod.POST), any(HttpEntity::class.java), eq(TinyUrlResponse::class.java)
        )
    }

    @Test
    fun generateTinyUrl_failure() {
        val tinyUrlRequest = TinyUrlRequest()
        tinyUrlRequest.url = "http://examplr.com/i-want-to-be-shorten/i-am-long"


        Mockito.`when`(
            restTemplate.exchange(
                anyString(), eq(HttpMethod.POST),
                any(HttpEntity::class.java), eq(
                    TinyUrlResponse::class.java
                )
            )
        ).thenReturn(ResponseEntity.internalServerError().build())

        Assertions.assertThrows(RuntimeException::class.java) {
            tinyUrlConnector!!.generateTinyUrl(
                "djhfjkd"
            )
        }

        Mockito.verify(restTemplate, times(1)).exchange(
            eq("https://api.tinyurl.com" + "/create"),
            eq(HttpMethod.POST), any(HttpEntity::class.java), eq(TinyUrlResponse::class.java)
        )
    }
}