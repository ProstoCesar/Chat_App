package com.example.chat_app.chat.view_model;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat_app.repository.MessageRepository
import com.example.chat_app.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatViewModel : ViewModel() {
    private val repository = MessageRepository
    val isLoading = MutableLiveData<Boolean>(false)

    /// Получится список всех сообщений
    fun fetchMessages() {
        // Прерываемся если уже что-то грузится
        if (isLoading.value != false) return

        GlobalScope.launch {
            try {
                isLoading.postValue(true)
                repository.fetchMessages()
            } catch (e: Exception) {
                print(e.toString());
            }
            isLoading.postValue(false)
        }
    }

    /// Отправляем сообщение, засовываем его в стек сообщений и обновляем список с сервера
    fun sendMessage(message: String?) {
        // Прерываемся если уже что-то грузится
        if (isLoading.value != false) return

        // Прерываемся, если сообщение пустое
        if (message == null) return

        // Если нет имени или почему-то самого юзера, то используем no_name
        val userName = UserRepository.user.value?.name ?: "no_name"

        GlobalScope.launch {
            try {
                isLoading.postValue(true)
                repository.sendMessage(userName, message)
            } catch (e: Exception) {

            }
            isLoading.postValue(false)

            // Обновляем список сообщений
            fetchMessages()
        }
    }
}