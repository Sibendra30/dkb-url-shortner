package com.dkb.urlshortner.entity

import java.util.Date

class UrlComponent {
    var alias: String? = null
    var tinyUrl: String? = null
    var actualUrl: String? = null
    var createdAt: Date? = null
    var expiresAt: Date? = null

}