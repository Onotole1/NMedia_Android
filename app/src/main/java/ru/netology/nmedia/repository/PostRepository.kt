package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun getData(): LiveData<List<Post>> //возвращаем данные подписки на наш пост
    fun likeById(id: Long)
    fun shareById(id: Long)
//    fun view()
}