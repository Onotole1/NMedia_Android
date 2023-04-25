package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.nmedia.databinding.PostDetailFragmentBinding
import ru.netology.nmedia.utils.textArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PostDetailFragmentBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()

        arguments?.let {
            val text = it.textArg
            binding.contentTextView.text = text
        }

        return binding.root
    }
}