package com.example.souq.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation {
    if (email.isEmpty())
        return RegisterValidation.Failed("Email can't be empty")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("wrong email format")

    return RegisterValidation.Sucess
}

fun validatePassword(password: String): RegisterValidation {
    if (password.isEmpty())
        return RegisterValidation.Failed("Password can't be empty")

    if (password.length < 6)
        return RegisterValidation.Failed("Password should contain 6 charters")
    else return RegisterValidation.Sucess
}
