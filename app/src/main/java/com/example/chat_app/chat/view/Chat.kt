package com.example.chat_app.chat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_app.R
import com.example.chat_app.chat.adapter.MessageAdapter
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.chat.view_model.ChatViewModel
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.chat_fragment.view.*
import com.example.chat_app.databinding.ChatFragmentBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = ChatFragmentBinding.inflate(inflater, container, false)

        val view = binding.root;

        chatViewModel.fetchMessages()

        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        view.recycler_view.adapter = MessageAdapter(chatViewModel.messages.value?.toMutableList() ?: mutableListOf(MessageModel("a", "a", "a")))



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMessagesUpdateObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.sendMessageButton.setOnClickListener {
            print("CLICK");
            chatViewModel.sendMessage(binding.messageTextField.text.toString())
        }
    }

    private  fun fragmentMessagesUpdateObserver() {
        chatViewModel.messages.observe(viewLifecycleOwner, Observer { messages -> binding.recyclerView.adapter = MessageAdapter(chatViewModel.messages.value?.toMutableList() ?: arrayOf(MessageModel("Евгений2", "Сообщение 2", "10:00")).toMutableList())})
    }
}