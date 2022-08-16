package com.dkb.urlshortner.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class ApplicationConfig {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder()
        .setConnectTimeout(Duration.ofSeconds(10))
        .build()
}