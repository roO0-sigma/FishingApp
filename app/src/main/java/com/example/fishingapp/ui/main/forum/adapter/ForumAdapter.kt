package com.example.fishingapp.ui.main.forum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.fishingapp.R
import com.example.fishingapp.ui.main.forum.model.ForumPost

class ForumAdapter(private val onItemClick: (ForumPost) -> Unit) :
    ListAdapter<ForumPost, ForumItemViewHolder>(ForumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forum_post, parent, false)
        return ForumItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumItemViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

private class ForumDiffCallback : DiffUtil.ItemCallback<ForumPost>() {
    override fun areItemsTheSame(oldItem: ForumPost, newItem: ForumPost): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ForumPost, newItem: ForumPost): Boolean {
        return oldItem == newItem
    }
} 