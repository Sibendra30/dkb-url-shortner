package com.dkb.urlshortner.api

import com.dkb.urlshortner.model.CreateShortUrlRequest
import com.dkb.urlshortner.model.CreateShortUrlResponse
import org.springframework.http.ResponseEntity

interface UrlShortnerApi {
    fun createShortUrl(createShortUrlRequest: CreateShortUrlRequest?): ResponseEntity<CreateShortUrlResponse>
}