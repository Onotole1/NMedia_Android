package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.dto.Draft

interface PostRepository {
    fun getData(): LiveData<List<Post>> //возвращаем данные подписки на наш пост
    fun getDraft(): LiveData<Draft> // возвращаем данные черновика
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun saveDraft(text: String)
}