package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel: PostViewModel by activityViewModels()

        val activityBinding = FragmentFeedBinding.inflate(layoutInflater)

        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
//                    newPostContract.launch(post.content)
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

                override fun onDetailsClicked(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_postDetailFragment)
                }
            }
        )


        viewModel.data.observe(viewLifecycleOwner)
        { posts ->
            adapter.submitList(posts)
        }

        activityBinding.list.adapter = adapter

        activityBinding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return activityBinding.root
    }
}