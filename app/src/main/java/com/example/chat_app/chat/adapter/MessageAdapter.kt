package com.example.chat_app.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.databinding.MessageAdapterBinding
import java.text.SimpleDateFormat

// Простой адаптер для сообщений
class MessageAdapter(private val arrayList: MutableList<MessageModel>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MessageAdapter.MessageViewHolder {
        val binding = MessageAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner  class MessageViewHolder(private val binding: MessageAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel){

            binding.textView.text = message.name
            binding.textView4.text = message.message
            // TODO: Нашел такой форматтер для работы с датами и не понимаю почему он желтый
            binding.textView5.text = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(message.time)
        }

    }

}