package ru.netology.nmedia


import android.R.attr.visible
import android.R.attr.x
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityBinding.root)


        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onClearEditing(post: Post) {
                    viewModel.clearEditing()
                }
            }
        )


        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                activityBinding.cancelGroup.visibility = View.GONE
                return@observe
            }

            activityBinding.cancelGroup.visibility = View.VISIBLE
            activityBinding.content.requestFocus()
            activityBinding.content.setText(it.content)
        }


        activityBinding.cancel.setOnClickListener {
            activityBinding.cancelGroup.visibility = View.GONE
            viewModel.clearEditing()
            activityBinding.content.clearFocus()
            activityBinding.content.setText("")
        }

        activityBinding.save.setOnClickListener {
            with(activityBinding.content) {
                val content = text.toString()
                if (content.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.empty_post_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(content)
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityBinding.list.adapter = adapter

    }
}