package com.example.chat_app.service.login.exception

class LoginException(val errorType: LoginErrorType, override val message: String): Throwable(message)