package com.example.chat_app.service.chat

import com.example.chat_app.chat.model.MessageModel
import com.parse.ParseObject
import com.parse.ParseQuery
import java.text.SimpleDateFormat
import java.util.*


class MessageService {
    fun getAllMessages(): List<MessageModel> {
        val firstObject = ParseQuery.getQuery<ParseObject>("Messages")
        var list = firstObject.find();
        return list.map { value ->
            MessageModel(
                (value.get("ownerName") ?: "undefined") as String,
                (value.get("message") ?: "") as String,
                (SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(value.get("date") ?: 0))
            )
        }
    }

    fun sendMessage(name: String, message: String) {
        var time = Date().time
        val myNewObject = ParseObject("Messages")
        myNewObject.put("ownerName", name)
        myNewObject.put("message", message)
        myNewObject.put("date", time)
        myNewObject.save()
    }
}