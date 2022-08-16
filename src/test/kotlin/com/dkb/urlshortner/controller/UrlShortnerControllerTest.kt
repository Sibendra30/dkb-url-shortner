package com.dkb.urlshortner.controller

import com.dkb.urlshortner.entity.UrlComponent
import com.dkb.urlshortner.model.CreateShortUrlRequest
import com.dkb.urlshortner.service.UrlShortnerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.http.server.reactive.MockServerHttpRequest.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest
internal class UrlShortnerControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var urlShortnerService: UrlShortnerService
    val LONG_URL = "https://www.linkedin.com/feed/"

    @Test
    fun createShortUrl() {
        val urlComponent: UrlComponent = UrlComponent();
        urlComponent.actualUrl = LONG_URL
        urlComponent.tinyUrl = "http://tiny.com/abc123"
        urlComponent.alias = "abc123"
        Mockito.`when`(urlShortnerService.createShortUrl(LONG_URL))
            .thenReturn(urlComponent)

        val objectMapper = ObjectMapper();

        val requestPayload: CreateShortUrlRequest = CreateShortUrlRequest()
        requestPayload.url = LONG_URL

        mockMvc.perform(MockMvcRequestBuilders.post("/url-shortner")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestPayload)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.id").value("abc123"))
            .andExpect(jsonPath("$.shortUrl").value("http://tiny.com/abc123"))
    }
}