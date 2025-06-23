package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.LeaderboardEntryDto
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class LeaderboardViewModel(context: Context) : ViewModel() {

    private val api = RetrofitHelper.getInstance(context).create(ApiService::class.java)

    var entries by mutableStateOf<List<LeaderboardEntryDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchLeaderboard() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                entries = api.getLeaderboard()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unexpected error"
            } finally {
                isLoading = false
            }
        }
    }
}