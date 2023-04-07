package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.WallService

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            authorTextView.text = post.author
            postDateTextView.text = post.published
            contentTextView.text = post.content

            like.setImageResource(
                if (post.likeByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )

            likesAmount.text = WallService.countAmountFormat(post.likes)
            shareAmount.text = WallService.countAmountFormat(post.shares)
            viewsAmount.text = WallService.countAmountFormat(post.views)

            like.setOnClickListener {
                listener.onLike(post)
            }

            share.setOnClickListener {
                listener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.posts_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}