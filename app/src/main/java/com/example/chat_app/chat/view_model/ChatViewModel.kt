package com.example.chat_app.chat.view_model;

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.repository.ChatRepository
import com.example.chat_app.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository
    val isLoading = MutableLiveData<Boolean>(false)

    /// Получится список всех сообщений
    fun fetchMessages() {
        // Прерываемся если уже что-то грузится
        if (isLoading.value == true) return

        GlobalScope.launch {
            try {
                isLoading.postValue(true)
                repository.fetchMessages()
            } catch (e: Exception) {
                // TODO: Добавить обработку ошибок
                Log.e("fetchMessages", e.message.toString())
            }
            isLoading.postValue(false)
        }
    }

    /// Отправляем сообщение, засовываем его в стек сообщений и обновляем список с сервера
    fun sendMessage(message: String?) {
        // Прерываемся если уже что-то грузится
        if (isLoading.value == true) return

        // Прерываемся, если сообщение пустое
        if (message == null) return

        // Если нет имени или почему-то самого юзера, то используем no_name
        val userName = UserRepository.user.value?.name ?: "no_name"

        GlobalScope.launch {
            try {
                isLoading.postValue(true)

                val message = MessageModel(
                    userName,
                    message,
                    Date().time
                )

                // Добавить сообщение в список сообщений
                repository.addNewMessage(message)

                // Отправить сообщение
                repository.sendMessage(message)
            } catch (e: Exception) {

            }
            isLoading.postValue(false)
        }
    }
}