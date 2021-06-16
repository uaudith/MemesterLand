package com.uaudith.memesterland.factory

import android.util.Log
import com.uaudith.memesterland.HttpClient
import com.uaudith.memesterland.memeSources.MemeSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

const val TAG = "LinksFactory"
class LinksFactory {
    private val sources = mutableSetOf<MemeSource>()
    private val imgLinks = mutableListOf<String>()
    private lateinit var scope : CoroutineScope
    private var cbAfterFetch: (Int, Int) -> Unit = { _: Int, _: Int -> }
    private val imgLinksLock = Mutex()

    fun addSource(source: MemeSource){
        sources.add(source)
        fetch(source)
    }
    fun addSource(vararg sources: MemeSource){
        sources.forEach {
            addSource(it)
        }
    }

    private fun fetch(source: MemeSource)= scope.launch {

        var startPos: Int
        Log.i(TAG,"fetching addresses from internet")
        source.fetch(HttpClient.client)
        val size = source.getTotalCount()
        imgLinksLock.withLock {
                startPos = imgLinks.size
//                Log.i(TAG,"start=$startPos and size=$size")
                imgLinks.addAll(source.getAll())
                withContext(Dispatchers.Main){
                    cbAfterFetch(startPos, size)
                }
            }

        }

    fun setCbOnFetch(block: (Int, Int)->Unit){
        cbAfterFetch = block
    }

    fun getTotalCount(): Int {
        return imgLinks.size
    }

    fun getUrlAt(position: Int): String {
        return imgLinks[position]
    }

    fun removeItem(position: Int){
        imgLinks.removeAt(position)
    }

    fun shuffleItems() {
        imgLinks.shuffle()
    }
    fun setScope(scope: CoroutineScope){
        this.scope = scope
    }
}