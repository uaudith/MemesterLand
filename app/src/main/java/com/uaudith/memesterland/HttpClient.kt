package com.uaudith.memesterland
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*

class HttpClient {
    companion object {
        val client = HttpClient(Android) {
            install(JsonFeature)
            install(Logging) {
                logger = Logger.DEFAULT
            }
            engine {
                // this: AndroidEngineConfig
                connectTimeout = 500_000
                socketTimeout = 500_000
//                proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("10.0.2.2", 8080))
            }
        }
    }
}