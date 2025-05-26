package com.example.fishingapp.ui.auth


import androidx.lifecycle.ViewModelProvider

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fishingapp.R
import com.example.fishingapp.SharedPreferencesManager
import com.example.fishingapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

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
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val tvRegister = view.findViewById<TextView>(R.id.tv_register)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signIn(email, password)
        }

        viewModel.authState.observe(viewLifecycleOwner) { exception ->
            if (exception == null) {
                sharedPreferencesManager.userLogIn()
                findNavController().navigate(R.id.action_login_to_home)
            } else {
                Toast.makeText(context, "Ошибка: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }

        tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}