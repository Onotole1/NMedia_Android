package ru.netology.nmedia

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(POST_NOTIFICATIONS), 100500)
        }

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
//                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
                findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = text
                        }
                    )
            }


            //обнуляем отработанный интент
            it.apply {// Устанавливаем ACTION_DEFAULT и удаляем дополнительный текст, чтобы избежать неожиданных действий в будущем
                action = Intent.ACTION_DEFAULT
                putExtra(Intent.EXTRA_TEXT, "")
            }
        }
    }
}