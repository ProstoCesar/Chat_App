package com.example.chat_app.repository

import androidx.lifecycle.MutableLiveData
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.service.chat.MessageService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

object MessageRepository {
    private val service = MessageService()

    val isLoading = MutableLiveData<Boolean>(false)
    val messages = MutableLiveData<List<MessageModel>>()

    fun fetchMessages() {
        try {
            isLoading.postValue(true)
            GlobalScope.launch {
                messages.postValue(service.getAllMessages())
            }
        } catch (e: Exception) {

        } finally {
            isLoading.postValue(false)
        }
    }

    fun sendMessage(name: String, message: String) {
        try {
            GlobalScope.launch {
                service.sendMessage(name,message)
            }
        } catch (e: Exception) {

        } finally {

        }
    }
}