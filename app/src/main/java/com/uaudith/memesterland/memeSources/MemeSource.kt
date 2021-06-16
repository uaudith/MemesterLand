package com.uaudith.memesterland.memeSources

import io.ktor.client.*

interface MemeSource {
    fun getTotalCount():Int
    fun getAll(): Set<String>
    suspend fun fetch(client: HttpClient)
}