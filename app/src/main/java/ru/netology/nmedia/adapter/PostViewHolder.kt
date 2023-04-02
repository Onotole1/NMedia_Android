package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.WallService

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
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
                onLikeClicked(post)
            }

            share.setOnClickListener {
                onShareClicked(post)
            }
        }
    }
}