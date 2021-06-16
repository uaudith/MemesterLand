package com.uaudith.memesterland

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uaudith.memesterland.factory.LinksFactory
import com.uaudith.memesterland.memeSources.Reddit

class MainViewModel: ViewModel() {
    val lf = LinksFactory()
    init {
        lf.setScope(viewModelScope)
        lf.addSource(
            Reddit("dankmemes"),
            Reddit("greentext")
        )
    }
}