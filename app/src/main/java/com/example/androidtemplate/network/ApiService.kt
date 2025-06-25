package com.example.androidtemplate.network

import com.example.androidtemplate.data.dtos.*
import com.example.androidtemplate.data.requests.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<AuthResponse>

    @GET("auth/me")
    suspend fun getCurrentUser(): Response<Parent>

    @GET("children")
    suspend fun getChildren(): Response<List<Child>>

    @GET("cards/parent/{parentId}")
    suspend fun getParentCards(@Path("parentId") parentId: Long): List<BankCardDto>

    @GET("/api/wallet/child/{childId}")
    suspend fun getWalletByChildId(@Path("childId") childId: Long): WalletResponseDto

    @GET("/api/tasks/child/{childId}")
    suspend fun getTasksForChild(@Path("childId") childId: Long): List<KidTaskDto>

    @GET("/api/leaderboard")
    suspend fun getLeaderboard(@Query("top") top: Int = 3): List<LeaderboardEntryDto>

    @POST("children/create")
    suspend fun createChild(@Body request: CreateChildRequest): Response<ResponseBody>

    @POST("cards/parent/{parentId}/new")
    suspend fun createParentCard(@Path("parentId") parentId: Long): BankCardDto

    @POST("wallet/child/{childId}/add-gems")
    suspend fun addGemsToChild(
        @Path("childId") childId: Long,
        @Body request: AddGemsRequest
    ): Response<WalletResponseDto>


}