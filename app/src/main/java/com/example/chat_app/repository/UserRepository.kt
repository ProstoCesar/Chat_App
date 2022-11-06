package com.example.chat_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chat_app.model.UserModel
import com.example.chat_app.service.LoginErrors
import com.example.chat_app.service.UserService


object UserRepository {
    private val service: UserService = UserService()

    var user: MutableLiveData<UserModel?> = MutableLiveData<UserModel?>(null)

    fun login(login: String, password: String) {
        fun positiveLoginCallback(user: UserModel) {
            this.user.value = user
        }

        fun negativeLoginCallback(error: LoginErrors, errorMessage: String) {
            Log.e("Login", error.name + errorMessage)
            user.value = null
        }
        service.login(login, password, ::positiveLoginCallback, ::negativeLoginCallback);
    }

    fun logout() {
        user.value = null
    }
}