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
import ru.netology.nmedia.FeedFragment.Companion.idArg
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.PostDetailFragmentBinding
import ru.netology.nmedia.dto.WallService
import ru.netology.nmedia.viewmodel.PostViewModel

class PostDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PostDetailFragmentBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()

        viewModel.data.observe(viewLifecycleOwner) { list ->
            list.find { it.id == arguments?.idArg }?.let { post ->
                binding.singlePost.apply {
                    PostViewHolder(
                        this,
                        object : PostListener {
                            override fun onRemove(post: Post) {
                                viewModel.removeById(post.id)
                                findNavController()
                                    .navigate(R.id.action_postDetailFragment_to_feedFragment2)
                            }

                            override fun onEdit(post: Post) {
                                viewModel.edit(post)
                                findNavController()
                                    .navigate(
                                        R.id.action_postDetailFragment_to_newPostFragment,
                                        Bundle().apply {
                                            textArg = post.content
                                        }
                                    )
                            }

                            override fun onShare(post: Post) {
                                viewModel.shareById(post.id)

                                val intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, post.content)
                                    type = "text/plain"
                                }

                                val startIntent =
                                    Intent.createChooser(intent, getString(R.string.app_name))

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
                                findNavController().navigate(
                                    R.id.action_postDetailFragment_to_feedFragment2,
                                )
                            }
                        }
                    ).bind(post)
                }
            }
        }

        return binding.root
    }
}