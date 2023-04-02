package ru.netology.nmedia


import android.R.attr.x
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityBinding.root)


        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            {
                viewModel.likeById(it.id)
            },
            {
                viewModel.shareById(it.id)
            }
        )

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityBinding.list.adapter = adapter

//
//        binding.views.setOnClickListener {
//            viewModel.view()
//        }
    }
}