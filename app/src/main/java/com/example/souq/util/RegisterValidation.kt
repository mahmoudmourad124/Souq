package com.example.souq.util

sealed class    RegisterValidation(){
    object Sucess:RegisterValidation()
    data class Failed(val message:String): RegisterValidation()
}
data class RegisterFieldState   (
    val email:RegisterValidation,
    val password:RegisterValidation
)
