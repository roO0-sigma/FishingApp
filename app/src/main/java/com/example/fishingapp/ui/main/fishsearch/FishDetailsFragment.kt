package com.example.fishingapp.ui.main.fishsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fishingapp.R
import com.example.fishingapp.data.model.Fish
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore

class FishDetailsFragment : Fragment() {
    private val args: FishDetailsFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()
    private var currentFish: Fish? = null

    private lateinit var imageViewFish: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewWaterType: TextView
    private lateinit var textViewSize: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var chipGroupBaits: ChipGroup
    private lateinit var chipGroupSeasons: ChipGroup
    private lateinit var textViewBestTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fish_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        loadFishDetails()
        
        view.findViewById<ImageView>(R.id.imageView).setOnClickListener { 
            findNavController().navigate(R.id.action_fishDetailsFragment_to_fishSearchFragment) 
        }
    }

    private fun initViews(view: View) {
        imageViewFish = view.findViewById(R.id.imageViewFish)
        textViewName = view.findViewById(R.id.textViewName)
        textViewWaterType = view.findViewById(R.id.textViewWaterType)
        textViewSize = view.findViewById(R.id.textViewSize)
        textViewDescription = view.findViewById(R.id.textViewDescription)
        chipGroupBaits = view.findViewById(R.id.chipGroupBaits)
        chipGroupSeasons = view.findViewById(R.id.chipGroupSeasons)
        textViewBestTime = view.findViewById(R.id.textViewBestTime)
    }

    private fun loadFishDetails() {
        db.collection("fish").document(args.fishId)
            .get()
            .addOnSuccessListener { document ->
                document.toObject(Fish::class.java)?.let { fish ->
                    currentFish = fish
                    displayFishDetails(fish)
                }
            }
    }

    private fun displayFishDetails(fish: Fish) {
        // Загрузка изображения
        Glide.with(this)
            .load(fish.photoUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageViewFish)

        // Установка текстовых полей
        textViewName.text = fish.name
        textViewWaterType.text = "Тип водоема: ${
            when(fish.waterType) {
                Fish.WATER_TYPE_LAKE -> "Озеро"
                Fish.WATER_TYPE_RIVER -> "Река"
                Fish.WATER_TYPE_SEA -> "Море"
                else -> fish.waterType
            }
        }"
        textViewSize.text = "Средний размер: ${fish.averageLength} см, вес: ${fish.averageWeight} кг"
        textViewDescription.text = fish.description
        textViewBestTime.text = "Лучшее время для ловли: ${fish.bestTimeOfDay}"

        // Добавление чипов для приманок
        chipGroupBaits.removeAllViews()
        fish.baitTypes.forEach { bait ->
            chipGroupBaits.addView(createChip(bait))
        }

        // Добавление чипов для сезонов
        chipGroupSeasons.removeAllViews()
        fish.seasonality.forEach { season ->
            chipGroupSeasons.addView(createChip(season))
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(requireContext()).apply {
            this.text = text
            isClickable = false
        }
    }
} 