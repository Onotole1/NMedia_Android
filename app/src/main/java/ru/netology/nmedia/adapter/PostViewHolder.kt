package ru.netology.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.provider.Settings.Global.getString
import android.widget.PopupMenu
import androidx.activity.result.launch
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.MainActivity
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

            like.isChecked = post.likeByMe

            like.text = WallService.countAmountFormat(post.likes)
            share.text = WallService.countAmountFormat(post.shares)
            views.text = WallService.countAmountFormat(post.views)

            like.setOnClickListener {
                listener.onLike(post)
            }

            share.setOnClickListener {
                listener.onShare(post)
            }

            videoContent.isVisible = !post.video.isNullOrBlank()

//            videoContent.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                startActivity(, intent)
//            }

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