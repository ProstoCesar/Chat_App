package com.example.chat_app.authentication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chat_app.R
import com.example.chat_app.authentication.view_model.LoginViewModel
import com.example.chat_app.databinding.LoginFragmentBinding
import com.example.chat_app.repository.UserRepository

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Присваиваем слушатели
        fragmentLoginUpdateObserver()
        // Присваиваем обработчики нажатий
        setupClickListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun fragmentLoginUpdateObserver() {
        // Переходим на основной экран, если пользователь появится в репо
        UserRepository.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                findNavController().navigate(R.id.login_to_app)
            }
        })

        // Скрываем кнопку входа и показываем лоадер, если что-то грузим и наоборот
        loginViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.loginProgressbar.visibility = View.VISIBLE
                binding.buttonEntry.visibility = View.GONE
            } else {
                binding.loginProgressbar.visibility = View.GONE
                binding.buttonEntry.visibility = View.VISIBLE
            }
        })

        // Если есть ошибка, то показываем её текст
        loginViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error != null) {
                binding.loginErrorMessage.visibility = View.VISIBLE
                binding.loginErrorMessage.text = error.message
            } else {
                binding.loginErrorMessage.visibility = View.GONE
                binding.loginErrorMessage.text = ""
            }
        })
    }

    private fun setupClickListeners() {
        // TODO: Не знал как еще повесить отлов изменения текста
        // Сбрасываем ошибку при изменении текста логина
        binding.loginLoginTextedit.doOnTextChanged { text, start, before, count ->
            binding.loginErrorMessage.visibility = View.GONE
            binding.loginErrorMessage.text = ""
        }
        // Сбрасываем ошибку при изменении текста пароля
        binding.loginPasswordTextedit.doOnTextChanged { text, start, before, count ->
            binding.loginErrorMessage.visibility = View.GONE
            binding.loginErrorMessage.text = ""
        }
        // При нажатии на кнопку логина логинимся
        binding.buttonEntry.setOnClickListener {
            val login = binding.loginLoginTextedit.text.toString()
            val password = binding.loginPasswordTextedit.text.toString()
            loginViewModel.login(login, password)

        }
    }
}