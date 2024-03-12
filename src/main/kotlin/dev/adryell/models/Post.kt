package dev.adryell.models

import kotlinx.serialization.Serializable

@Serializable
data class Post(val id: String, val title: String, val description: String)

val PostStorage = mutableListOf<Post>()