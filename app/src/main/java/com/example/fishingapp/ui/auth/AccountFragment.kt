package com.example.fishingapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.R
import com.example.fishingapp.SharedPreferencesManager
import com.example.fishingapp.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
    private val binding: FragmentAccountBinding by lazy { FragmentAccountBinding.inflate(layoutInflater) }
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        val user = viewModel.getCurrentUser()
        binding.emailTV.text = user?.email.toString()
        binding.exitLayout.setOnClickListener {
            viewModel.signOut()
            sharedPreferencesManager.forgetUser()
            findNavController().navigate(R.id.action_accountFragment_to_registerFragment)
        }
        binding.imageView.setOnClickListener { findNavController().navigate(R.id.action_accountFragment_to_homeFragment) }
    }

}