package ru.netology.nmedia


import android.R.attr.x
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorTextView.text = post.author
                postDateTextView.text = post.published
                contentTextView.text = post.content

                if (post.likeByMe) {
                    like.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    like.setImageResource(R.drawable.baseline_favorite_border_24)
                }

                likesAmount.text = WallService.countAmountFormat(post.likes)
                shareAmount.text = WallService.countAmountFormat(post.shares)
                viewsAmount.text = WallService.countAmountFormat(post.views)
            }
        }

        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.share()
        }

        binding.views.setOnClickListener {
            viewModel.view()
        }
    }
}