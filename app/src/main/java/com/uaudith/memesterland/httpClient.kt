package com.uaudith.memesterland
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import java.net.Proxy
import java.net.InetSocketAddress

class httpClient {
    companion object {
        val client = HttpClient(Android) {
            install(JsonFeature)
            install(Logging) {
                logger = Logger.DEFAULT
            }
            engine {
                // this: AndroidEngineConfig
                connectTimeout = 100_000
                socketTimeout = 100_000
//                proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("10.0.2.2", 8080))
            }
        }
    }
}