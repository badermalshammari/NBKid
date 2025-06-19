package com.example.androidtemplate.data.dtos

data class User(
    var username: String,
    var password: String,
    val id: Long? = null,
    val token: String? = null,
)
