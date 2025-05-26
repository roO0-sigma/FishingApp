package com.example.fishingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.databinding.FragmentHomeBinding
import com.example.fishingapp.ui.auth.AuthViewModel

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        // Настройка кликов по карточкам
        binding.cardVideoGuides.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_videoGuidesFragment)
        }

        binding.cardFishSearch.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_fishSearchFragment)
        }

        binding.cardForum.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_forumFragment)
        }

        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_accountFragment2)
        }
    }

} 