package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun getData(): LiveData<List<Post>> //возвращаем данные подписки на наш пост
    fun getDraft(): LiveData<String> // возвращаем данные черновика
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}