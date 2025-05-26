package com.example.fishingapp.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.R
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчики кликов по карточкам
        view.findViewById<MaterialCardView>(R.id.card_video_guides).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_videoGuidesFragment)
        }

        view.findViewById<MaterialCardView>(R.id.card_fish_search).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_fishSearchFragment)
        }

        view.findViewById<MaterialCardView>(R.id.card_forum).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_forumFragment)
        }
    }
}