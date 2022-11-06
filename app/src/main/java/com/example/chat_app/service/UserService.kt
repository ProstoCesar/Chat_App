package com.example.chat_app.service

import com.example.chat_app.model.UserModel
import com.parse.ParseException
import com.parse.ParseUser

enum class LoginErrors {
    NO_USER,
}

class UserService {
    fun login(email: String, password: String, positiveCallback: (UserModel) -> Unit , negativeCallback: (error: LoginErrors, errorMessage: String) -> Unit) {
        ParseUser.logInInBackground(
            email, password
        ) { user: ParseUser?, e: ParseException? ->
            if (user != null) {
                positiveCallback(
                    UserModel(
                        user.username,
                        user.email,
                        user.sessionToken,
                    )
                )
            } else {
                negativeCallback(LoginErrors.NO_USER, e?.message ?: "")
            }
        }
    }
}