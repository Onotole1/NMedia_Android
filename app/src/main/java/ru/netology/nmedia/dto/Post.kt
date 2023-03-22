package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    var likes: Int = 3999,
    var shares: Int = 999,
    var views: Int = 999
)