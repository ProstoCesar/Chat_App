package com.example.chat_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.service.chat.ChatService

/* TODO: Репозиториями стал пользоваться по привычке. Мне привычнее, когда между контроллером
 экрана и получением данных есть прослойка, которая эти данные откуда-то берет и предоставляет */
object ChatRepository {
    private val service = ChatService()

    val messages = MutableLiveData<MutableList<MessageModel>>()

    fun fetchMessages() {
        messages.postValue(service.getAllMessages())
    }

    fun sendMessage(message: MessageModel) {
        service.sendMessage(message)
    }

    fun addNewMessage(message:MessageModel) {
        // TODO: Выглядит, как костыль, но больше я ничего не нашел
        messages.value?.add(message)
        messages.postValue(messages.value)
    }
}