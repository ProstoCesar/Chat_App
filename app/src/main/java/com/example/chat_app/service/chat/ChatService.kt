package com.example.chat_app.service.chat

import com.example.chat_app.chat.model.MessageModel
import com.parse.ParseObject
import com.parse.ParseQuery


class ChatService {
    fun getAllMessages(): MutableList<MessageModel> {
        val messageObject = ParseQuery.getQuery<ParseObject>("Messages")
            return messageObject.find().map { value ->
            // TODO: Иногда значение было Long, а иногда - Int. Пришлось костылять
            val date = value.get("date") ?: 0
            val lol = if (date is Long) {
                date
            } else {
                (date as Int).toLong()
            }
            MessageModel(
                (value.get("ownerName") ?: "undefined") as String,
                (value.get("message") ?: "") as String,
                lol
            )
        }.toMutableList()
    }

    fun sendMessage(message: MessageModel) {
        val messageObject = ParseObject("Messages")
        messageObject.put("ownerName", message.name)
        messageObject.put("message", message.message)
        messageObject.put("date", message.time)
        messageObject.save()
    }
}