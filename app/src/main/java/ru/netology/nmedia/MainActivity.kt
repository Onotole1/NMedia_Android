package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.root.setOnClickListener {
            println("Root")
        }

        binding.like.setOnClickListener {
            println("Like")
        }

        binding.imageView2.setOnClickListener {
            println("Ava")
        }

        setContentView(binding.root)

        val post = Post(
            0,
            "Нетология, Университет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать бастрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            "21 мая в 18:36",
            false
        )

        with(binding) {
            authorTextView.text = post.author
            postDateTextView.text = post.published
            contentTextView.text = post.content

            if (post.likeByMe) {
                like?.setImageResource(R.drawable.baseline_favorite_24)
            }

            val divideLikesToThousand = post.likes / 1000

            likesAmount.text = post.likes.toString()

            if(post.likes >= 1000) {
                Log.d("$divideLikesToThousand", "sdsds")
                likesAmount.text = "$divideLikesToThousand" + "K"
            }

            shareAmount.text = post.shares.toString()



            like?.setOnClickListener {
                post.likeByMe = !post.likeByMe
                post.likes = if (post.likeByMe) {
                    post.likes + 1
                } else {
                    post.likes - 1
                }

                likesAmount.text = post.likes.toString()

                like.setImageResource(
                    if (post.likeByMe) {
                        R.drawable.baseline_favorite_24
                    } else {
                        R.drawable.baseline_favorite_border_24
                    }
                )

                if(post.likes >= 1000) {
                    likesAmount.text = "$divideLikesToThousand" + "K"
                }
            }


            share?.setOnClickListener {
                post.shares += 1
                shareAmount.text = post.shares.toString()

                val divideSharesToThousand = post.shares / 1000

                if(post.shares >= 1000) {
                    shareAmount.text = "$divideSharesToThousand" + "K"
                }
            }

            views?.setOnClickListener {
                post.views += 1
                viewsAmount.text = post.views.toString()

                val divideViewsToThousand = post.views / 1000

                if(post.views >= 1000) {
                    viewsAmount.text = "$divideViewsToThousand" + "K"
                }
            }
        }
    }
}