package com.uaudith.memesterland.memeSources

import android.util.Log
import com.uaudith.memesterland.entities.RedditResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

const val TAG = "Reddit"
class Reddit(subredditName: String): MemeSource {
    private val uri = "https://www.reddit.com/r/$subredditName/.json?limit=50"
    private val memeList = mutableSetOf<String>()
    override fun getTotalCount(): Int {
        return memeList.size
    }

    override fun getAll(): Set<String> {
        return memeList
    }

    override suspend fun fetch(client: HttpClient) {
        try {
            val response = client.request<RedditResponse>(uri) {
                // Configure request parameters exposed by HttpRequestBuilder
                method = HttpMethod.Get
            }
            response.data.children.forEach {
                memeList.add(it.data.url)
            }
        }catch (e: Exception){
            Log.w(TAG,e.message?:"Something Wrong")
        }

    }
}