package com.uaudith.memesterland.factory

import com.uaudith.memesterland.entities.LinkList
import io.ktor.client.request.*
import com.uaudith.memesterland.httpClient
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LinksFactory {
    private val sources = mutableSetOf<String>()
    private val imgLinks = mutableListOf<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun addSourceLink(uri: String){
        sources.add(uri)
        scope.launch {
            getImageUris(uri)
        }
    }

    private suspend fun getImageUris(uri: String) {
        val client = httpClient.client
        val response = client.request<LinkList>(uri) {
            // Configure request parameters exposed by HttpRequestBuilder
            method = HttpMethod.Get
        }
        response.data.children.forEach {
            imgLinks.add(it.data.url)
        }

    }
}