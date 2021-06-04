package com.uaudith.memesterland.entities
// Used to serialize the json response from reddit

data class LinkList(
    val kind: String,
    val data: Data
)

data class Data(
    val after: String,
    val children: List<Child>

)

data class Child(
    val kind: String,
    val data: ChildData
)

data class ChildData(
    val url: String
)