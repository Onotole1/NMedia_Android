package ru.netology.nmedia.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Draft
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory


private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likeByMe = false,
    video = ""
)

private val emptyDraft = Draft(
    id = 0,
    draftText = ""
)

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInMemory(
        application.applicationContext,
        AppDb.getInstance(application).postDao
    )

    val data: LiveData<List<Post>> = repository.getData()

    private val draftList: LiveData<List<Draft>> = repository.getDraft()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    private val edited = MutableLiveData(empty)

    private val draftEdited = MutableLiveData(emptyDraft)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }

        edited.value = empty
    }

    fun saveDraft(text: String) {
        draftEdited.value?.let {
            repository.saveDraft(text)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun clearEditing() {
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let { post ->
            if(content != post.content) {
                edited.value = post.copy(
                    published = "now",
                    author = "me",
                    content = content
                )
            }
        }
    }
}