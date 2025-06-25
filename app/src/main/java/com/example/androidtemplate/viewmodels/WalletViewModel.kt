package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.AddGemsRequest
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)

    private val _walletState = MutableStateFlow<WalletResponseDto?>(null)
    val walletState: StateFlow<WalletResponseDto?> = _walletState

    var isLoading = MutableStateFlow(false)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    fun fetchWallet(childId: Long) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = apiService.getWalletByChildId(childId)
                _walletState.value = response
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
    fun addGemsToChild(childId: Long, gems: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.addGemsToChild(childId, AddGemsRequest(gems))
                onResult(response.isSuccessful)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}