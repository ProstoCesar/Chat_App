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

    /// Функция логина. При удаче в UserRepo устанавливается переменная user
    /// При ошибке, устанавливается переменная error
    fun login(login: String, password: String) {
        // TODO: У меня пока сложно с потоками и я нашел это, чтобы запустить код в отдельном потоке
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