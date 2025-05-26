package com.example.fishingapp.ui.main.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.fishingapp.ui.main.forum.model.ForumPost

class CreatePostFragment : Fragment() {
    private lateinit var titleEditText: TextInputEditText
    private lateinit var contentEditText: TextInputEditText
    private lateinit var createButton: MaterialButton

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleEditText = view.findViewById(R.id.editTextTitle)
        contentEditText = view.findViewById(R.id.editTextContent)
        createButton = view.findViewById(R.id.buttonCreatePost)

        createButton.setOnClickListener {
            createPost()
        }
    }

    private fun createPost() {
        val title = titleEditText.text?.toString()?.trim() ?: ""
        val content = contentEditText.text?.toString()?.trim() ?: ""

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Необходимо войти в аккаунт", Toast.LENGTH_SHORT).show()
            return
        }

        // Показываем индикатор загрузки
        createButton.isEnabled = false
        createButton.text = "Создание..."

        val post = ForumPost(
            title = title,
            content = content,
            authorEmail = currentUser.email ?: ""
            // timestamp будет установлен автоматически сервером благодаря @ServerTimestamp
        )

        db.collection("posts")
            .add(post)
            .addOnSuccessListener {
                Toast.makeText(context, "Пост создан успешно", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack(R.id.forumFragment, false)
            }
            .addOnFailureListener { e ->
                Log.e("CreatePost", "Error creating post", e)
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                // Восстанавливаем кнопку
                createButton.isEnabled = true
                createButton.text = "Создать пост"
            }
    }
} 