package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun getData(): LiveData<Post> //возвращаем данные подписки на наш пост
    fun like()
    fun share()
    fun view()
}