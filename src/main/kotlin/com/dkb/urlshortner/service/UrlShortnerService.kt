package com.dkb.urlshortner.service

import com.dkb.urlshortner.entity.UrlComponent

interface UrlShortnerService {
    fun createShortUrl(longUrl: String?): UrlComponent?
}