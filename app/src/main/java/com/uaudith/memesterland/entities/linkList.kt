package com.uaudith.memesterland.entities

import androidx.annotation.Keep

// Used to serialize the json response from reddit

@Keep
data class LinkList(
    val kind: String,
    val data: Data
)
@Keep
data class Data(
    val after: String,
    val children: List<Child>

)
@Keep
data class Child(
    val kind: String,
    val data: ChildData
)
@Keep
data class ChildData(
    val url: String,
    val name: String
)