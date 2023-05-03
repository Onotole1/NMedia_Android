package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Draft


class PostRepositoryInMemory(
    private val context: Context, //если обращаемся в коде к контексту, то тогда объявляем его как свойство - через val
    private val dao: PostDao
) : PostRepository {

    companion object {
        private const val FILE_NAME = "posts.json"
        private const val DRAFT_FILE_NAME = "draft.json"
    }

    private val gson = Gson()
    private val typeDraft = TypeToken.getParameterized(Draft::class.java).type

    private var posts: List<Post> = emptyList()

    private val data = MutableLiveData(posts)

    private var draft: Draft = readDrafts()

    private val draftData = MutableLiveData(draft)

    //init используется для передачи свойству (posts) класса значения из конструктора
    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getData(): LiveData<List<Post>> = data

    override fun getDraft(): LiveData<Draft> = draftData

    private fun draftSync() {
        context.openFileOutput(DRAFT_FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(draft))
        }
    }

    private fun readDrafts(): Draft {
        val file = context.filesDir.resolve(DRAFT_FILE_NAME)

        return if (file.exists()) {
            context.openFileInput(DRAFT_FILE_NAME).bufferedReader().use {
                gson.fromJson(it, typeDraft)
            }
        } else {
            draft
        }
    }


    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                dao.likeById(id)
                post.copy(
                    likeByMe = !post.likeByMe,
                    likes = if (post.likeByMe) post.likes - 1 else post.likes + 1
                )
            } else { //базу подключили, тут вопросы есть?
                post
            }
        }

        data.value =
            posts // оповещаем что-то изменилось, с помощью setValue записываем в MutableLiveData значение. Подписчикам придёт обновленный список
    }


    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                dao.shareById(id)
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
        dao.removeById(id)

        data.value = posts
    }

    override fun save(post: Post) {
        val postId = post.id
        val savedValue = dao.save(post)

        posts = if (post.id == 0L) {
            listOf(savedValue) + posts
        } else {
            posts.map {
                if (it.id != postId) it else savedValue
            }
        }

        data.value = posts
    }

    override fun saveDraft(text: String) {
        if (!text.isNullOrBlank()) {
            draft = draft.copy(draftText = text)
        }
    }
}