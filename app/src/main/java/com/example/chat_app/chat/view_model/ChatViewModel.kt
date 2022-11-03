package com.example.chat_app.chat.view_model;

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat_app.chat.model.MessageModel
import java.time.LocalDateTime

class ChatViewModel: ViewModel(){
    val messages = MutableLiveData<Array<MessageModel>>();

    fun fetchMessages(){
        messages.value = arrayOf(MessageModel("Евгений1", "Сообщение 1", "10:00"),
            MessageModel("Евгений2", "Сообщение 2", "10:01"),)
        println("AAAAAAAAAAAAAAAAAAA" + messages.value);

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String?) {
        println("AAAAAAAAAAAAAAAAAAA" + messages.value?.size);
        if (message == null) return
        println("AAAAAAAAAAAAAAAAAAA2222" + messages.value?.first().toString());
        messages.value = arrayOf(MessageModel("Евгений2", "Сообщение 2", LocalDateTime.now().toString()),
            MessageModel("Евгений3", "Сообщение 3", "10:01"),)
        println("AAAAAAAAAAAAAAAAAAA2222" + messages.value?.first().toString());
    }
}