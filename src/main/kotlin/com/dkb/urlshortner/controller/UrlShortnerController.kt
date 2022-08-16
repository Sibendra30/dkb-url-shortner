package com.dkb.urlshortner.controller

import com.dkb.urlshortner.api.UrlShortnerApi
import com.dkb.urlshortner.connector.TinyUrlConnector
import com.dkb.urlshortner.entity.UrlComponent
import com.dkb.urlshortner.model.CreateShortUrlRequest
import com.dkb.urlshortner.model.CreateShortUrlResponse
import com.dkb.urlshortner.service.UrlShortnerService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/url-shortner")
class UrlShortnerController @Autowired constructor(private val urlShortnerService: UrlShortnerService):UrlShortnerApi {
    @PostMapping
    override fun createShortUrl(@RequestBody createShortUrlRequest: CreateShortUrlRequest?): ResponseEntity<CreateShortUrlResponse> {
        val schemes = arrayOf("http", "https")
        val urlValidator = UrlValidator(schemes)

        if(createShortUrlRequest?.url == null
            || !urlValidator.isValid(createShortUrlRequest!!.url)) {
            throw IllegalArgumentException("Missing or invalid request")
        }
        val createShortUrlResponse: CreateShortUrlResponse = CreateShortUrlResponse()
        val urlComponent: UrlComponent? = urlShortnerService.createShortUrl(createShortUrlRequest!!.url)
        createShortUrlResponse.id = urlComponent!!.alias
        createShortUrlResponse.shortUrl = urlComponent!!.tinyUrl
        return ResponseEntity.of(Optional.ofNullable(createShortUrlResponse));
    }
}