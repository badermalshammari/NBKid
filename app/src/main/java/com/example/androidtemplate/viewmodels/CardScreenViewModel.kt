package com.example.androidtemplate.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.BankCardDto
import com.example.androidtemplate.data.dtos.CreateChildRequest
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.network.RetrofitHelper
import com.example.androidtemplate.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardScreenViewModel(context: Context) : ViewModel() {

    private val apiService: ApiService =
        RetrofitHelper.getInstance(context).create(ApiService::class.java)

    private val _cards = MutableStateFlow<List<BankCardDto>>(emptyList())
    val cards: StateFlow<List<BankCardDto>> = _cards

    private val _selectedCard = MutableStateFlow<BankCardDto?>(null)
    val selectedCard: StateFlow<BankCardDto?> = _selectedCard



    var isLoading by mutableStateOf(false)
        private set
    var errorMessage: String? by mutableStateOf(null)
        internal set


    private val _displayZuzu = MutableStateFlow(true)
    val displayZuzu: StateFlow<Boolean> = _displayZuzu



    fun fetchCards(parentId: Long) {
        viewModelScope.launch {
            try {
                val fetched = apiService.getParentCards(parentId)
                _cards.value = fetched
                _selectedCard.value = fetched.firstOrNull()
            } catch (_: Exception) {
                // You may log or emit error state here
            }
        }
    }
    fun createChild(
        request: CreateChildRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.createChild(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }


    fun selectCard(card: BankCardDto) {
        _selectedCard.value = card
    }

    fun toggleZuzuAccounts(show: Boolean) {
        _displayZuzu.value = show
    }
}