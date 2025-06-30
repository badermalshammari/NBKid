package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.BankCardDto
import com.example.androidtemplate.data.dtos.CreateChildRequest
import com.example.androidtemplate.data.dtos.CreateParentCardRequest
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

    private val _displayZuzu = MutableStateFlow(false)
    val displayZuzu: StateFlow<Boolean> = _displayZuzu

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage: String? by mutableStateOf(null)
        internal set

    fun fetchCards(parentId: Long) {
        viewModelScope.launch {
            try {
                val fetched = apiService.getParentCards(parentId)
                _cards.value = fetched
                _selectedCard.value = fetched.firstOrNull()
            } catch (e: Exception) {
                errorMessage = "Failed to fetch cards: ${e.message}"
            }
        }
    }
    fun createParentCard(
        request: CreateParentCardRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {

                val response = apiService.createParentCard(request)
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

    fun toggleCardActivation(
        cardId: Long,
        active: Boolean,
        onSuccess: (BankCardDto) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.toggleCardActivation(cardId, active)
                if (response.isSuccessful) {
                    val updatedCard = response.body()!!
                    _cards.value = _cards.value.map {
                        if (it.cardId == cardId) updatedCard else it
                    }
                    onSuccess(updatedCard)
                } else {
                    onError(Exception("Failed with code ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                onError(e)
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