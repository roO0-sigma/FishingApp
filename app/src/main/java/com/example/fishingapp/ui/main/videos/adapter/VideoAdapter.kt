package com.example.fishingapp.ui.main.videos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishingapp.R
import com.example.fishingapp.data.model.VideoGuide

class VideoAdapter(
    private val items: List<VideoGuide>,
    private val onItemClick: (VideoGuide) -> Unit
) : RecyclerView.Adapter<VideoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_guide, parent, false)
        return VideoItemViewHolder(view, onItemClick)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}