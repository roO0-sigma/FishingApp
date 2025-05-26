package com.example.fishingapp.ui.main.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.R
import com.example.fishingapp.databinding.FragmentForumTopicBinding
import com.example.fishingapp.ui.main.forum.model.ForumPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ForumTopicFragment : Fragment() {
    private val binding: FragmentForumTopicBinding by lazy { FragmentForumTopicBinding.inflate(layoutInflater) }

    private var postId: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getString(ARG_POST_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener { findNavController().navigate(R.id.action_forumTopicFragment_to_forumFragment) }
        loadPost()
    }

    private fun loadPost() {
        postId?.let { id ->
            db.collection("posts").document(id)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        // Handle error
                        return@addSnapshotListener
                    }

                    val post = snapshot?.toObject(ForumPost::class.java)
                    post?.let { displayPost(it) }
                }
        }
    }

    private fun displayPost(post: ForumPost) {
        val contentTextView = binding.textViewTopicContent
        val authorTextView = binding.textViewTopicAuthor
        val dateTextView = binding.textViewTopicDate
        val titleTextView = binding.textViewTopicTitle
        contentTextView.text = post.content
        authorTextView.text = post.authorEmail
        dateTextView.text = formatDate(post.timestamp)
        titleTextView.text = post.title
    }

    private fun formatDate(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(timestamp.toDate())
    }

    companion object {
        private const val ARG_POST_ID = "post_id"

        fun newInstance(postId: String) =
            ForumTopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POST_ID, postId)
                }
            }
    }
} 