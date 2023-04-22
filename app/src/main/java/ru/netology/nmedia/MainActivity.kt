package ru.netology.nmedia


import android.R.attr.visible
import android.R.attr.x
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityCardPostBinding
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onResume() {
        viewModel.clearEditing()
        super.onResume()
    }

    val viewModel: PostViewModel by viewModels()

    lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)


        //объявление переменной; регистрация контракта
        val newPostContract = registerForActivityResult(NewPostActivity.Contract) { result ->
            result ?: return@registerForActivityResult //если null
            viewModel.changeContent((result)) //если не null
            viewModel.save()
        }


        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    newPostContract.launch(post.content)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)

                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val startIntent = Intent.createChooser(intent, getString(R.string.app_name))

                    startActivity(startIntent)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onClearEditing(post: Post) {
                    viewModel.clearEditing()
                }

                override fun onOpenVideo(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    startActivity(intent)
                }
            }
        )


        viewModel.data.observe(this)
        { posts ->
            adapter.submitList(posts)
        }

        activityBinding.list.adapter = adapter

        activityBinding.add.setOnClickListener {
            newPostContract.launch("")
        }
    }
}