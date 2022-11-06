package com.example.chat_app.authentication.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat_app.repository.UserRepository
import com.example.chat_app.service.login.exception.LoginException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<LoginException?>(null)

    fun login(login: String, password: String) {
        GlobalScope.launch {
                try {
                    error.postValue(null)
                    isLoading.postValue(true)
                    UserRepository.login(login, password)
                } catch (err: LoginException) {
                    error.postValue(err)
                } finally {
                    isLoading.postValue(false)
                }
            }
        }
}