package com.dkb.urlshortner

import com.dkb.urlshortner.model.CreateShortUrlRequest
import com.dkb.urlshortner.model.CreateShortUrlResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(
    classes = arrayOf(UrlshortnerApplication::class),
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortnerIntgTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun createShortUrl_validRequest() {
        val createUrlRequest:CreateShortUrlRequest = CreateShortUrlRequest();
        createUrlRequest.url = "https://linkedin.com/feed"
        val httpHeaders = HttpHeaders();
        val httpEntity = HttpEntity(createUrlRequest, httpHeaders)
        val result = restTemplate.exchange("/url-shortner", HttpMethod.POST, httpEntity, CreateShortUrlResponse::class.java);

        Assertions.assertNotNull(result)
        Assertions.assertEquals(HttpStatus.OK, result?.statusCode)
    }

    @Test
    fun createShortUrl_invalidUrl() {
        val createUrlRequest:CreateShortUrlRequest = CreateShortUrlRequest();
        createUrlRequest.url = "https://linkedin/feed"
        val httpHeaders = HttpHeaders();
        val httpEntity = HttpEntity(createUrlRequest, httpHeaders)
        val result = restTemplate.exchange("/url-shortner", HttpMethod.POST, httpEntity, CreateShortUrlResponse::class.java);

        Assertions.assertNotNull(result)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)
    }
}