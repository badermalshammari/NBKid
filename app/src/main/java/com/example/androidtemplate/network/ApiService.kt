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
    suspend fun getWalletByChildId(@Path("childId") childId: Long?): WalletResponseDto

    @GET("/api/tasks/child/{childId}")
    suspend fun getTasksForChild(@Path("childId") childId: Long): List<KidTaskDto>

    @GET("/api/leaderboard/top")
    suspend fun getLeaderboard(@Query("top") top: Int = 10): List<LeaderboardEntryDto>



    @POST("children/create")
    suspend fun createChild(@Body request: CreateChildRequest): Response<ResponseBody>


    @POST("cards/parent/{parentId}/new")
    suspend fun createParentCard(@Body request: CreateParentCardRequest): Response<BankCardDto>

    @POST("cards/{cardId}/activation")
    suspend fun toggleCardActivation(
        @Path("cardId") cardId: Long,
        @Query("active") active: Boolean
    ): Response<BankCardDto>

    @GET("/api/children/{childId}/store")
    suspend fun getChildStoreItems(@Path("childId") childId: Long): List<ChildStoreItemDto>

    @POST("/api/children/{childId}/item/{itemId}/toggle")
    suspend fun toggleHidden(
        @Path("childId") childId: Long,
        @Path("itemId") itemId: Long
    ): ChildStoreItemDto

    @POST("/api/children/{childId}/item/{itemId}/wishlist/toggle")
    suspend fun toggleWishlist(
        @Path("childId") childId: Long,
        @Path("itemId") itemId: Long
    ): ChildStoreItemDto

    @POST("wallet/child/{childId}/add-gems")
    suspend fun addGemsToChild(
        @Path("childId") childId: Long,
        @Body request: AddGemsRequest
    ): Response<WalletResponseDto>

    @POST("tasks")
    suspend fun createTask(@Body request: CreateTaskRequest): Response<KidTaskDto>

    @POST("transactions/transfer")
    suspend fun transfer(@Body request: TransferRequest): TransactionDto

    @POST("store/items/order")
    suspend fun orderItem(
        @Query("childId") childId: Long,
        @Query("childStoreItemId") childStoreItemId: Long
    ): OrderedItemDto

    @POST("tasks/complete")
    suspend fun markTaskAsFinished(
        @Query("childId") childId: Long,
        @Query("taskId") taskId: Long
    ): Response<TaskProgressDto>

    @GET("content")
    suspend fun getAllVideos(): List<VideoOption>


    @GET("store/orders/{childId}")
    suspend fun getOrders(@Path("childId") childId: Long): List<OrderedItemDto>



}