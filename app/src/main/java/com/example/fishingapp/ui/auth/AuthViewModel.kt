package com.example.fishingapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuthException

class AuthViewModel : ViewModel() {
    private val _authState = MutableLiveData<FirebaseAuthException?>()
    val authState: LiveData<FirebaseAuthException?> = _authState

    // LiveData для отслеживания текущего пользователя
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    init {
        // Инициализация текущего пользователя при создании ViewModel
        _currentUser.value = FirebaseAuth.getInstance().currentUser
    }

    fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = null // Успешный вход
                    _currentUser.value = FirebaseAuth.getInstance().currentUser // Обновляем текущего пользователя
                } else {
                    _authState.value = task.exception as? FirebaseAuthException
                }
            }
    }

    fun signUp(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = null // Успешная регистрация
                    _currentUser.value = FirebaseAuth.getInstance().currentUser // Обновляем текущего пользователя
                } else {
                    _authState.value = task.exception as? FirebaseAuthException
                }
            }
    }

    fun signOut() {
        // Выход из аккаунта
        FirebaseAuth.getInstance().signOut()
        _currentUser.value = null // Очищаем текущего пользователя
        _authState.value = null // Очищаем состояние авторизации
    }

    /**
     * Получить текущего пользователя (если он авторизован).
     */
    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}
