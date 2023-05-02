package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    val likes: Int = 999,
    val shares: Int = 999,
    val views: Int = 999,
    val video: String?
)

