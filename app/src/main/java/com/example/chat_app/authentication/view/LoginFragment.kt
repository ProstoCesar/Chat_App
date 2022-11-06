package com.example.chat_app.authentication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chat_app.R
import com.example.chat_app.authentication.view_model.LoginViewModel
import com.example.chat_app.chat.adapter.MessageAdapter
import com.example.chat_app.chat.model.MessageModel
import com.example.chat_app.chat.view_model.ChatViewModel
import com.example.chat_app.databinding.FragmentFirstBinding
import com.example.chat_app.databinding.LoginFragmentBinding
import com.example.chat_app.repository.UserRepository
import com.parse.ParseUser
import java.util.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val userRepository = UserRepository;

    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        fragmentLoginUpdateObserver()
        setupClickListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private  fun fragmentLoginUpdateObserver() {
        UserRepository.user
        userRepository.user.observe(viewLifecycleOwner, Observer { _ -> view.findViewById<Button>(R.id.buttonEntry).setOnClickListener {
            findNavController().navigate(R.id.login_to_app)
        } })

        loginViewModel.messages.observe(viewLifecycleOwner, Observer { messages -> binding.recyclerView.adapter = MessageAdapter(chatViewModel.messages.value?.toMutableList() ?: arrayOf(
            MessageModel("Евгений2", "Сообщение 2", "10:00")
        ).toMutableList())
        })
    }

    private fun setupClickListeners() {
        var login = binding.loginLoginTextedit.text.toString()
        var password = binding.loginPasswordTextedit.text.toString()
        binding.buttonEntry.setOnClickListener {
            UserRepository.login(login, password)
        }
    }
}