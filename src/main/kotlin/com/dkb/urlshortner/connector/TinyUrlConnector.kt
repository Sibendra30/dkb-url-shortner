package com.dkb.urlshortner.connector

import com.dkb.urlshortner.entity.TinyUrlData
import com.dkb.urlshortner.error.ApiException
import com.dkb.urlshortner.entity.TinyUrlRequest
import com.dkb.urlshortner.entity.TinyUrlResponse
import com.dkb.urlshortner.entity.UrlComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

@Component
class TinyUrlConnector @Autowired constructor(private val restTemplate: RestTemplate) {
    private val BASE_URL = "https://api.tinyurl.com"
    fun generateTinyUrl(longUrl: String?): TinyUrlData? {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer hYmrzfz1pntFdz38XlKuiTIyj5lpJpNxuNlzY7M8rUJRKMNoi1oCCh8xhwBa");

        val tinyUrlRequest = TinyUrlRequest()
        tinyUrlRequest.url = longUrl
        val tinyUrlRequestHttpEntity = HttpEntity(tinyUrlRequest, headers)

        val response = restTemplate.exchange(
            "$BASE_URL/create",
            HttpMethod.POST, tinyUrlRequestHttpEntity, TinyUrlResponse::class.java
        )

        if (response.statusCode.is2xxSuccessful && response.body !== null) {
            return response.body!!.data;
        }
        throw ApiException("Url shortening failure")
    }
}