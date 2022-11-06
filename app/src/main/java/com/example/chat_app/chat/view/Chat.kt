package com.example.chat_app.chat.view

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_app.chat.adapter.MessageAdapter
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.chat.view_model.ChatViewModel
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.chat_fragment.view.*
import com.example.chat_app.databinding.ChatFragmentBinding
import com.example.chat_app.repository.MessageRepository
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat.postDelayed

/**
 * A simple [Fragment] subclass.
 * Use the [Chat.newInstance] factory method to
 * create an instance of this fragment.
 */
class Chat : Fragment() {
    // View Binding
    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)

        val view = binding.root;

        chatViewModel.fetchMessages()
        Handler(Looper.getMainLooper()).postDelayed({
            chatViewModel.fetchMessages()
        }, 10000)
        view.messages_recycler_view.layoutManager = LinearLayoutManager(activity)
        view.messages_recycler_view.adapter =
            MessageAdapter(MessageRepository.messages.value?.toMutableList() ?: mutableListOf())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMessagesUpdateObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Отправляем текст и очищаем поле
        binding.sendMessageButton.setOnClickListener {
            chatViewModel.sendMessage(binding.messageTextField.text.toString())
            binding.messageTextField.text?.clear()
        }
    }

    private fun fragmentMessagesUpdateObserver() {
        MessageRepository.messages.observe(
            viewLifecycleOwner,
            Observer { messages ->
                binding.messagesRecyclerView.adapter =
                    MessageAdapter(MessageRepository.messages.value?.toMutableList() ?: mutableListOf())
            }
        )

        chatViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.messagesProgressbar.visibility = View.VISIBLE
                binding.sendMessageButton.isEnabled = false
            } else {
                binding.messagesProgressbar.visibility = View.GONE
                binding.sendMessageButton.isEnabled = true
            }
        })
    }
}