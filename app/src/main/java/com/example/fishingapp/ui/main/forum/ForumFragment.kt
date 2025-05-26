package com.example.fishingapp.ui.main.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishingapp.R
import com.example.fishingapp.databinding.FragmentForumBinding
import com.example.fishingapp.ui.main.forum.adapter.ForumAdapter
import com.example.fishingapp.ui.main.forum.model.ForumPost
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ForumFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var forumAdapter: ForumAdapter
    private lateinit var fabCreatePost: FloatingActionButton
    private val binding: FragmentForumBinding by lazy { FragmentForumBinding.inflate(layoutInflater) }
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ForumFragment", "onCreateView called")
        return try {
            binding.root
        } catch (e: Exception) {
            Log.e("ForumFragment", "Error inflating layout", e)
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ForumFragment", "onViewCreated called")

        binding.imageView.setOnClickListener { findNavController().navigate(R.id.action_forumFragment_to_homeFragment) }

        try {
            recyclerView = view.findViewById(R.id.recyclerViewForum)
            fabCreatePost = view.findViewById(R.id.fabCreatePost)

            setupRecyclerView()
            setupFab()
            loadPosts()
        } catch (e: Exception) {
            Log.e("ForumFragment", "Error in onViewCreated", e)
            Toast.makeText(context, "Ошибка инициализации форума", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        Log.d("ForumFragment", "Setting up RecyclerView")
        try {
            forumAdapter = ForumAdapter { post ->
                findNavController().navigate(
                    ForumFragmentDirections.actionForumFragmentToForumTopicFragment(post.id)
                )
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = forumAdapter
            }
        } catch (e: Exception) {
            Log.e("ForumFragment", "Error setting up RecyclerView", e)
        }
    }

    private fun setupFab() {
        fabCreatePost.setOnClickListener {
            try {
                if (auth.currentUser != null) {
                    findNavController().navigate(
                        ForumFragmentDirections.actionForumFragmentToCreatePostFragment()
                    )
                } else {
                    Toast.makeText(context, "Необходимо войти в аккаунт", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ForumFragment", "Error in FAB click", e)
            }
        }
    }

    private fun loadPosts() {
        Log.d("ForumFragment", "Loading posts from Firestore")
        try {
            db.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e("ForumFragment", "Error loading posts", e)
                        Toast.makeText(context, "Ошибка загрузки постов: ${e.message}", Toast.LENGTH_LONG).show()
                        return@addSnapshotListener
                    }

                    val posts = snapshot?.documents?.mapNotNull { doc ->
                        try {
                            doc.toObject(ForumPost::class.java)?.copy(id = doc.id)
                        } catch (e: Exception) {
                            Log.e("ForumFragment", "Error converting document to post", e)
                            null
                        }
                    } ?: listOf()

                    Log.d("ForumFragment", "Loaded ${posts.size} posts")
                    forumAdapter.submitList(posts)
                }
        } catch (e: Exception) {
            Log.e("ForumFragment", "Error setting up posts listener", e)
        }
    }
} 