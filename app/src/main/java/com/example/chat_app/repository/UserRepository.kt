package com.example.chat_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chat_app.model.UserModel
import com.example.chat_app.service.UserService
import com.example.chat_app.service.login.exception.LoginException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


object UserRepository {
    private val service: UserService = UserService()

    var user = MutableLiveData<UserModel?>(null)

    fun login(login: String, password: String) {
        user.postValue(service.login(login, password))
    }

    fun logout() {
        user.value = null
    }
}