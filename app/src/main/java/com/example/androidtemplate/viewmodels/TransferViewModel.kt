package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.requests.TransferRequest
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.launch

class TransferViewModel(
    private val context: Context,
    private val apiService: ApiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var successMessage by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf<String?>(null)

    fun performTransfer(request: TransferRequest) {
        viewModelScope.launch {
            try {
                isLoading = true
                successMessage = null
                errorMessage = null

                val response = apiService.transfer(request)
                successMessage = "Transferred ${response.amount} successfully!"
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Transfer failed"
            } finally {
                isLoading = false
            }
        }
    }
}