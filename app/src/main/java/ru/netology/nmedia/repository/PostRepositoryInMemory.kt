package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Draft
import ru.netology.nmedia.dto.VideoLink
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryInMemory(
    private val context: Context, //если обращаемся в коде к контексту, то тогда объявляем его как свойство - через val
    private val dao: PostDao
) : PostRepository {

    companion object {
        private const val DRAFT_FILE_NAME = "draft.json"
        private const val VIDEO_LINK_FILE_NAME = "videoLink.json"
    }

    private val gson = Gson()
    private val typeDraft = TypeToken.getParameterized(Draft::class.java).type
    private val typeVideoLink = TypeToken.getParameterized(VideoLink::class.java).type

    private var draft: Draft = readDrafts()
        set(value) {
            field = value
            draftSync()
            draftData.value = value
        }

    private var videoLink: VideoLink = readVideoLink()
        set(value) {
            field = value
            draftSync()
            videoLinkData.value = value
        }

    private val draftData = MutableLiveData(draft)
    private val videoLinkData = MutableLiveData(videoLink)

    //init используется для передачи свойству (posts) класса значения из конструктора


    override fun getData() = dao.getAll().map {
        list -> list.map { it.toDto() }
    }

    override fun getDraft(): LiveData<Draft> = draftData

    override fun getVideoLink(): LiveData<VideoLink> = videoLinkData

    private fun draftSync() {
        context.openFileOutput(DRAFT_FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(draft))
        }
    }

    private fun videoLinkSync() {
        context.openFileOutput(VIDEO_LINK_FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(videoLink))
        }
    }

    private fun readDrafts(): Draft {
        val file = context.filesDir.resolve(DRAFT_FILE_NAME)

        return if (file.exists()) {
            context.openFileInput(DRAFT_FILE_NAME).bufferedReader().use {
                gson.fromJson(it, typeDraft)
            }
        } else {
            Draft("")
        }
    }

    private fun readVideoLink(): VideoLink {
        val file = context.filesDir.resolve(VIDEO_LINK_FILE_NAME)

        return if (file.exists()) {
            context.openFileInput(VIDEO_LINK_FILE_NAME).bufferedReader().use {
                gson.fromJson(it, typeVideoLink)
            }
        } else {
            VideoLink("")
        }
    }


    override fun likeById(id: Long) = dao.likeById(id)

    override fun shareById(id: Long) = dao.shareById(id)

    override fun removeById(id: Long) = dao.removeById(id)

    override fun save(post: Post) = dao.save(PostEntity.fromDto(post))

    override fun saveDraft(text: String) {
        draft = draft.copy(draftText = text)
    }

    override fun saveVideoLink(link: String) {
        videoLink = videoLink.copy(videoLink = link)
    }
}