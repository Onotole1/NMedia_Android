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
        private const val DRAFT_FILE_NAME = "drafts.json"
    }

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var posts: List<Post> = readPosts()
        set(value) {
            field = value
            sync()
        }

    private var drafts: List<Draft> = readDrafts()
        set(value) {
            field = value
            sync()
        }

    private val data = MutableLiveData(posts)
    private val draftList = MutableLiveData(drafts)


    //init используется для передачи свойству (posts) класса значения из конструктора
    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getData(): LiveData<List<Post>> = data
    override fun getDraft(): LiveData<List<Draft>> = draftList


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


    private fun readDrafts(): List<Draft> {
        val file = context.filesDir.resolve(DRAFT_FILE_NAME)

        return if (file.exists()) {
            context.openFileInput(DRAFT_FILE_NAME).bufferedReader().use {
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
        for (draft in drafts) {
            if (draft.id == 0L) {
                drafts = listOf(
                    draft.copy(
                        id = (drafts.firstOrNull()?.id ?: 0L) + 1,
                        draftText = text
                    )
                ) + drafts
                draftList.value = drafts
                return
            }

            drafts = drafts.map {
                if(it.id != draft.id) it else it.copy(draftText = draft.draftText)
            }

            draftList.value = drafts
        }
    }
}