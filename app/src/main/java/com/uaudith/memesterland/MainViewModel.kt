package com.uaudith.memesterland

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uaudith.memesterland.factory.LinksFactory
import com.uaudith.memesterland.memeSources.Reddit

class MainViewModel: ViewModel() {
    val lfMain = LinksFactory()
    val lfFav = LinksFactory()
    init {
        lfMain.setScope(viewModelScope)
        lfMain.addSource(
            Reddit("dankmemes")
        )

        lfFav.setScope(viewModelScope)
        lfFav.addSource(
            Reddit("greentext")
        )
    }
}