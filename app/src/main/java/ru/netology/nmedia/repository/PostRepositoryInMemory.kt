package ru.netology.nmedia.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post


class PostRepositoryInMemory(
    private val context: Context //если обращаемся в коде к контексту, то тогда объявляем его как свойство - через val
) : PostRepository {

    companion object {
        private const val FILE_NAME = "posts.json"
    }

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var posts: List<Post> = readPosts()
        set(value) {
            field = value
            sync()
        }

    private val data = MutableLiveData(posts)
    private val draftList = MutableLiveData<String>()

    override fun getData(): LiveData<List<Post>> = data
    override fun getDraft(): LiveData<String> = draftList

    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    private fun readPosts(): List<Post> {
        val file = context.filesDir.resolve(FILE_NAME)

        return if (file.exists()) {
            context.openFileInput(FILE_NAME).bufferedReader().use {
                gson.fromJson(it, type)
            }
        } else {
            emptyList()
        }
    }


    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likeByMe = !post.likeByMe,
                    likes = if (post.likeByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }

        data.value =
            posts // оповещаем что-то изменилось, с помощью setValue записываем в MutableLiveData значение. Подписчикам придёт обновленный список
    }


    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }

        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter {
            it.id != id
        }

        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = (posts.firstOrNull()?.id ?: 0L) + 1
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
    }
}