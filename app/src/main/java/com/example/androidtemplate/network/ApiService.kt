package com.example.androidtemplate.network

import com.example.androidtemplate.data.dtos.*
import com.example.androidtemplate.data.requests.*
import com.example.androidtemplate.data.responses.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<AuthResponse>


    @GET("auth/me")
    suspend fun getCurrentUser(): Response<User>

    @GET("children")
    suspend fun getChildren(): Response<List<Child>>

}