package com.mv1998.medronic.data.network

import com.mv1998.medronic.data.models.PhotoDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.request

class PhotoClient(private val httpClient: HttpClient) {
    suspend fun getPhotos() : List<PhotoDto> {
        return httpClient
            .get("https://jsonplaceholder.typicode.com/albums/1/photos")
            .body()
    }
}