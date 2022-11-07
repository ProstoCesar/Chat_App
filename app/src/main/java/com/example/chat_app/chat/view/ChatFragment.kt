package com.example.chat_app.chat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_app.chat.adapter.MessageAdapter
import com.example.chat_app.chat.view_model.ChatViewModel
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.chat_fragment.view.*
import com.example.chat_app.databinding.ChatFragmentBinding
import com.example.chat_app.repository.ChatRepository
import android.os.Handler
import android.os.Looper

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
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

        // Запускаем загрузку сообщений во время загрузки фрагмента
        chatViewModel.fetchMessages()

        // Вешаем таймер на загрузку сообщений раз в 10 секунд
        Handler(Looper.getMainLooper()).postDelayed({
            chatViewModel.fetchMessages()
        }, 10000)

        // Не совсем пока понимаю эту строку
        view.messages_recycler_view.layoutManager = LinearLayoutManager(activity)
        // Присваиваем адаптер, который отвечает за отображение данных в одном элементе списка
        view.messages_recycler_view.adapter =
            MessageAdapter(ChatRepository.messages.value?.toMutableList() ?: mutableListOf())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Присваиваем слушатели
        fragmentMessagesUpdateObserver()
        // Присваиваем обработчики нажатий
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
        // При обновлении списка сообщений, обновляем адаптер
        ChatRepository.messages.observe(
            viewLifecycleOwner,
            Observer { messages ->
                binding.messagesRecyclerView.adapter =
                    MessageAdapter(ChatRepository.messages.value?.toMutableList() ?: mutableListOf())
            }
        )

        // Если что-то грузится, то показываем лоадер и отключаем кнопку отправки сообщений
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