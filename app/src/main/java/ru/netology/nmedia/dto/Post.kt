package ru.netology.nmedia

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    var likes: Int = 999,
    var shares: Int = 999,
    var views: Int = 999
)

