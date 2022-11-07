package com.example.chat_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.chat_app.model.UserModel
import com.example.chat_app.service.login.UserService


object UserRepository {
    private val service: UserService = UserService()

    var user = MutableLiveData<UserModel?>(null)

    fun login(login: String, password: String) {
        user.postValue(service.login(login, password))
    }
}