package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.CardPostBinding


interface PostListener {
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onClearEditing(post: Post)
    fun onOpenVideo(post: Post)
    fun onDetailsClicked(post: Post)
}

class PostAdapter(
    private val listener: PostListener
) : ListAdapter<Post, PostViewHolder>(
        PostDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(
            binding = binding,
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}