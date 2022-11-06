package com.example.chat_app.service.chat

import com.example.chat_app.chat.model.MessageModel
import com.parse.ParseObject
import com.parse.ParseQuery


class MessageService {
    fun getAllMessages(): List<MessageModel> {
        val firstObject = ParseQuery.getQuery<ParseObject>("Messages")
        var list = firstObject.find().map { value ->
            MessageModel(
                (value.get("ownerName") ?: "undefined") as String,
                (value.get("message") ?: "") as String,
                "10:00"
            )
        }
        return list
    }

    fun sendMessage(name: String, message: String) {
        val myNewObject = ParseObject("Messages")
        myNewObject.put("ownerName", name)
        myNewObject.put("message", message)
        myNewObject.save()
    }
}