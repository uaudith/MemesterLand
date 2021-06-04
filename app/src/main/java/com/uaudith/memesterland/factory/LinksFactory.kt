package com.uaudith.memesterland.factory

import android.util.Log
import com.uaudith.memesterland.entities.LinkList
import io.ktor.client.request.*
import com.uaudith.memesterland.httpClient
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "LinksFactory"
class LinksFactory {
    private val sources = mutableSetOf<String>()
    private val imgLinks = mutableListOf<String>()
    private val scope = CoroutineScope(Dispatchers.IO)
    private var cbAfterSource: () -> Unit = {}

    fun addSourceLink(uri: String){
        sources.add(uri)
        scope.launch {
            getImageUris(uri)
            // link list download is finished
            withContext(Dispatchers.Main){
                Log.i(TAG,"Calling to reset After source added")
                cbAfterSource()
            }
        }
    }

    fun addCallbackAfterSourceAdded(block: ()->Unit){
        cbAfterSource = block
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
    fun getTotalCount(): Int {
        return imgLinks.size
    }

    fun getUrlAt(position: Int): String {
        return imgLinks[position]
    }
}