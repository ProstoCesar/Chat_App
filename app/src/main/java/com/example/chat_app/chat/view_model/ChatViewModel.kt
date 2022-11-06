package com.example.chat_app.chat.view_model;

import androidx.lifecycle.ViewModel
import com.example.chat_app.repository.MessageRepository
import com.example.chat_app.repository.UserRepository

class ChatViewModel: ViewModel(){
    private val repository = MessageRepository
    val messages = repository.messages;

    fun fetchMessages(){
        repository.fetchMessages()
    }

    fun sendMessage(message: String?) {
        if (message == null) return
        repository.sendMessage(UserRepository.user.value?.name ?: "no_name", message)
        fetchMessages()
    }
}