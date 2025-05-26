package com.example.fishingapp.ui.main.videos.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fishingapp.R
import com.example.fishingapp.data.model.VideoGuide

class VideoItemViewHolder(
    private val view: View,
    private val onItemClick: (VideoGuide) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(video: VideoGuide) {
        val title = view.findViewById<TextView>(R.id.textViewTitle)
        val description = view.findViewById<TextView>(R.id.textViewDescription)
        val thumbnail = view.findViewById<ImageView>(R.id.imageViewThumbnail)
        val duration = view.findViewById<TextView>(R.id.textViewDuration)
        val views = view.findViewById<TextView>(R.id.textViewViews)

        title.text = video.title
        description.text = video.description
        duration.text = video.duration ?: "00:00"
        views.text = "${video.views ?: 0} просмотров"

        // Загрузка превью через Glide
        Glide.with(view)
            .load(video.thumbnailUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_video)
            .into(thumbnail)

        view.setOnClickListener {
            onItemClick(video)
        }
    }
}