package com.example.fishingapp.ui.main.fishsearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fishingapp.R
import com.example.fishingapp.data.model.Fish

class FishAdapter(private val onItemClick: (Fish) -> Unit) :
    ListAdapter<Fish, FishAdapter.FishViewHolder>(FishDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fish, parent, false)
        return FishViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FishViewHolder(
        itemView: View,
        private val onItemClick: (Fish) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewFish)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val waterTypeTextView: TextView = itemView.findViewById(R.id.textViewWaterType)
        private val sizeTextView: TextView = itemView.findViewById(R.id.textViewSize)

        fun bind(fish: Fish) {
            nameTextView.text = fish.name
            waterTypeTextView.text = when(fish.waterType) {
                Fish.WATER_TYPE_LAKE -> "Озеро"
                Fish.WATER_TYPE_RIVER -> "Река"
                Fish.WATER_TYPE_SEA -> "Море"
                else -> fish.waterType
            }
            sizeTextView.text = "Средний размер: ${fish.averageLength} см, вес: ${fish.averageWeight} кг"

            Glide.with(itemView.context)
                .load(fish.photoUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView)

            itemView.setOnClickListener { onItemClick(fish) }
        }
    }
}

private class FishDiffCallback : DiffUtil.ItemCallback<Fish>() {
    override fun areItemsTheSame(oldItem: Fish, newItem: Fish): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fish, newItem: Fish): Boolean {
        return oldItem == newItem
    }
} 