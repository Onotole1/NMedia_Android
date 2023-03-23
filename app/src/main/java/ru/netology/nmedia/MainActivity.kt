package ru.netology.nmedia

import android.R.attr.x
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt


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


            val divideLikesToThousand = post.likes.toDouble() / 1000
            val divideLikesToThousandInt = (post.likes / 1000)
            val divideLikesToMillion = post.likes.toDouble() / 1_000_000;
            val divideLikesToMillionInt = post.likes / 1_000_000;
            val z = 0.1 * floor(10 * divideLikesToThousand)
            val zMil = 0.1 * floor(10 * divideLikesToMillion)
            val s = String.format("%.1f", z)
            val sMil = String.format("%.1f", zMil)

            likesAmount.text = post.likes.toString()

            if(post.likes >= 0 && post.likes < 1000) {
                likesAmount.text = post.likes.toString()
            } else if(post.likes >= 1000 && post.likes < 10000) {
                Log.d("$divideLikesToThousand", "sdsds")
                likesAmount.text = "$s" + "K"
            } else if(post.likes >= 10000 && post.likes < 1_000_000) {
                likesAmount.text = "$divideLikesToThousandInt" + "K"
            } else if (post.likes >= 1_000_000 && post.likes < 10_000_000) {
                likesAmount.text = "$sMil" + "M"
            } else {
                likesAmount.text = "$divideLikesToMillionInt" + "M"
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

            likesAmount.text = post.views.toString()

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