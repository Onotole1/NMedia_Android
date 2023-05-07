package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    val likes: Int = 999,
    val shares: Int = 999,
    val views: Int = 999,
    val video: String?
) {
    fun toDto() = Post(id, author, content, published, likeByMe, likes, shares, views, video)

    companion object {
        fun fromDto(post: Post) = with(post) {
            PostEntity(id, author, content, published, likeByMe, likes, shares, views, video)
        }
    }
}