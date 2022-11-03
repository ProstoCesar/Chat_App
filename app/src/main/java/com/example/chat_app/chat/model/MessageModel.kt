package com.example.chat_app.chat.model

class MessageModel (val name: String, val message: String, val time: String) {
    override fun toString(): String {
        return "$name $message $time"
    }

}