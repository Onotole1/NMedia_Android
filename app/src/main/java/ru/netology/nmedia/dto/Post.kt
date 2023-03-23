package ru.netology.nmedia

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    var likes: Int = 1_300_000,
    var shares: Int = 999,
    var views: Int = 999
)

//fun formatNumbersCounters(num: Int, post: Post, viewElement: View) {
//    if(post.likes >= 0 && post.likes < 1000) {
//        viewElement.text = post.likes.toString()
//    } else if(post.likes >= 1000 && post.likes < 10000) {
//        Log.d("$divideLikesToThousand", "sdsds")
//        likesAmount.text = "$s" + "K"
//    } else if(post.likes >= 10000 && post.likes < 1_000_000) {
//        likesAmount.text = "$divideLikesToThousandInt" + "K"
//    } else if (post.likes >= 1_000_000 && post.likes < 10_000_000) {
//        likesAmount.text = "$sMil" + "M"
//    } else {
//        likesAmount.text = "$divideLikesToMillionInt" + "M"
//    }
//}