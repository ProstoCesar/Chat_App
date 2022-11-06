package com.example.chat_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.service.chat.MessageService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.Delayed

object MessageRepository {
    private val service = MessageService()

    val messages = MutableLiveData<List<MessageModel>>()

    fun fetchMessages() {
        Thread.sleep(2_000)
        messages.postValue(service.getAllMessages())
    }

    fun sendMessage(name: String, message: String) {
        service.sendMessage(name, message)
    }
}