package com.example.chat_app.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_app.R
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.chat.view.Chat
import com.example.chat_app.databinding.ChatFragmentBinding
import com.example.chat_app.databinding.MessageAdapterBinding
import org.w3c.dom.Text

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
            binding.textView5.text = message.time
        }

    }

}