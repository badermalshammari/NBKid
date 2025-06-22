package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.launch

class WalletViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)

    var wallet by mutableStateOf<WalletResponseDto?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchWallet(childId: Long) {
        viewModelScope.launch {
            isLoading = true
            try {
                wallet = apiService.getWalletByChildId(childId)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}