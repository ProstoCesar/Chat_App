package com.example.chat_app.service.login

import android.util.Log
import com.example.chat_app.model.UserModel
import com.example.chat_app.service.login.exception.LoginErrorType
import com.example.chat_app.service.login.exception.LoginException
import com.parse.ParseException
import com.parse.ParseUser

class UserService {
    fun login(
        email: String,
        password: String,
    ): UserModel? {
        var user: UserModel?

        try {
            val parseUser = ParseUser.logIn(
                email, password
            )

            if (parseUser != null) {
                user = UserModel(
                    parseUser.username,
                    parseUser.email,
                    parseUser.sessionToken
                )
            } else {
                throw ParseException(0, "")
            }
        } catch (err: Exception) {
            if (err is ParseException) {
                throw LoginException(LoginErrorType.INCORRECT_LOGIN_OR_PASSWORD, "Неверный логин/пароль")
            }

            throw LoginException(LoginErrorType.BAD_CONNECTION, "Ошибка соединения")
        }

        return user
    }

}