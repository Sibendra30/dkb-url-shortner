package com.dkb.urlshortner.error

class ApiException(override val message: String): RuntimeException() {
}