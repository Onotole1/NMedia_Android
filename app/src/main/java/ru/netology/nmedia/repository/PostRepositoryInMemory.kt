package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemory : PostRepository {
    private var post = listOf(
        Post(
            id = 0,
            author = "Нетология, Университет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать бастрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likeByMe = false
        ),
        Post(
            id = 1,
            author = "Нетология, Университет-профессий будущего",
            content = "Привет, kjdhjghjdfhjghjdfhgjhjdfhgjhjkdfhg это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать бастрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likeByMe = false
        )
    )

    private val data = MutableLiveData(post)

    override fun getData(): LiveData<List<Post>> = data


    override fun likeById(id: Long) {
        post = post.map { post ->
            if (post.id == id) {
                post.copy(likeByMe = !post.likeByMe)
            } else {
                post
            }
        }

        data.value = post // оповещаем что-то изменилось, с помощью setValue записываем в MutableLiveData значение. Подписчикам придёт обновленный список
    }


    override fun shareById(id: Long) {
        post = post.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }

        data.value = post
    }

//    override fun view() {
//        post = post.copy(
//            views = post.views + 1
//        )
//        data.value = post
//    }
}