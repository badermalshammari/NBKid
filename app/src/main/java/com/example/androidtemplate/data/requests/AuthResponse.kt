package com.example.androidtemplate.data.requests


data class AuthResponse(
    val token: String,
    val parent: RegisterRequest
)