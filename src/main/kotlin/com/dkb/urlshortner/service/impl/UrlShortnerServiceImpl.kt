package com.dkb.urlshortner.service.impl

import com.dkb.urlshortner.connector.TinyUrlConnector
import com.dkb.urlshortner.entity.UrlComponent
import com.dkb.urlshortner.error.ApiException
import com.dkb.urlshortner.service.UrlShortnerI
import com.dkb.urlshortner.service.UrlShortnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UrlShortnerServiceImpl @Autowired constructor(private val tinyUrlConnector: TinyUrlConnector) : UrlShortnerService {
    override fun createShortUrl(longUrl: String?): UrlComponent? {
        val tinyUrlData = tinyUrlConnector.generateTinyUrl(longUrl) ?: throw ApiException("Invalid url data")
        val urlComponent = UrlComponent()
        urlComponent.actualUrl = tinyUrlData.url
        urlComponent.tinyUrl = tinyUrlData.tiny_url
        urlComponent.alias = tinyUrlData.alias
        return urlComponent
    }
}
