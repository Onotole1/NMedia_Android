package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.utils.TextArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by TextArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()

        arguments?.let {
            val text = it.textArg
            binding.content.setText(text)
        }

        viewModel.draft.observe(viewLifecycleOwner) {
            if(arguments?.textArg.isNullOrBlank()) {
                binding.content.setText(it.draftText)
            }
        }

        binding.content.requestFocus()

        binding.ok.setOnClickListener {
            if(!binding.content.text.isNullOrBlank()) {
                val content = binding.content.text.toString()
                val videoContentLink = binding.contentVideoLink.text.toString()
                viewModel.changeContent(content, videoContentLink)
                viewModel.save()
                viewModel.saveDraft("")
            }
            findNavController().navigateUp() // up - вверх/назад
        }


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(arguments?.textArg.isNullOrBlank()) {
                        viewModel.saveDraft(binding.content.text.toString())
                        viewModel.saveVideoLink(binding.contentVideoLink.text.toString())
                    } else {
                        viewModel.saveDraft("")
                        viewModel.saveVideoLink("")
                        viewModel.clearEditing()
                    }

                    findNavController().navigateUp()
                }
            }
        )


        return binding.root
    }

}