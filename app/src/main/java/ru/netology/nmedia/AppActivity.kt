package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityAppBinding
import ru.netology.nmedia.utils.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)

        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.empty_post_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
            } else {
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }

            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = text
                    }
                )
            findNavController(R.id.nav_host_fragment)
                .navigate(
                    R.id.action_feedFragment_to_postDetailFragment,
                    Bundle().apply {
                        textArg = text
                    }
                )
        }
    }
}