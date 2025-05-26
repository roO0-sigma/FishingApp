package com.example.fishingapp.ui.main.forum.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fishingapp.R
import com.example.fishingapp.ui.main.forum.model.ForumPost
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ForumItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
    private val authorTextView: TextView = itemView.findViewById(R.id.textViewAuthor)
    private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
    //private val contentPreviewTextView: TextView = itemView.findViewById(R.id.textViewContentPreview)

    fun bind(post: ForumPost, onItemClick: (ForumPost) -> Unit) {
        titleTextView.text = post.title
        authorTextView.text = post.authorEmail
        dateTextView.text = formatDate(post.timestamp)
        //contentPreviewTextView.text = post.content.take(100) + if (post.content.length > 100) "..." else ""

        itemView.setOnClickListener { onItemClick(post) }
    }

    private fun formatDate(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(timestamp.toDate())
    }
} 