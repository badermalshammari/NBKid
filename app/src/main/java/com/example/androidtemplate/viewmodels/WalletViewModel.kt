package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.AddGemsRequest
import com.example.androidtemplate.data.dtos.OrderedItemDto
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)


    var orders by mutableStateOf<List<OrderedItemDto>>(emptyList())
        private set

    var is_Loading by mutableStateOf(false)
        private set

    var error_Message by mutableStateOf<String?>(null)
        private set


    private val _walletState = MutableStateFlow<WalletResponseDto?>(null)
    val walletState: StateFlow<WalletResponseDto?> = _walletState

    var isLoading = MutableStateFlow(false)
        private set

    var errorMessage = MutableStateFlow<String?>(null)
        private set

    fun fetchWallet(childId: Long?) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = apiService.getWalletByChildId(childId?.toLong())
                _walletState.value = response
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }


    fun fetchOrders(childId: Long) {
        viewModelScope.launch {
            is_Loading = true
            error_Message = null
            try {
                orders = apiService.getOrders(childId)
            } catch (e: Exception) {
                error_Message = e.localizedMessage
            } finally {
                is_Loading = false
            }
        }
    }
        fun orderItem(
            childId: Long,
            itemId: Long,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    val response = apiService.orderItem(childId, itemId)
                    onSuccess()
                } catch (e: Exception) {
                    onError("Error: ${e.message}")
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
