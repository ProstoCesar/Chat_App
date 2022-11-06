package com.example.chat_app.authentication.view_model

import androidx.lifecycle.ViewModel
import com.example.chat_app.repository.UserRepository

class LoginViewModel: ViewModel() {
    fun login(login: String, password: String) {
        UserRepository.login(login, password)
    }
}