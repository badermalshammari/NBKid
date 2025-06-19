package com.example.androidtemplate.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.User
import com.example.androidtemplate.data.requests.AuthResponse
import com.example.androidtemplate.data.requests.RegisterRequest
import com.example.androidtemplate.data.responses.TokenResponse
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import com.example.androidtemplate.utils.TokenManager
import kotlinx.coroutines.launch

class NBKidsViewModel(
    private val context: Context,
    private val apiService: ApiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)
) : ViewModel() {


    private val TAG = "AuthViewModel"

    var token: TokenResponse? by mutableStateOf(null)
        private set
    var user: User? by mutableStateOf(null)
        private set
    var name by mutableStateOf("")
    var nbkidsAccounts: List<User> by mutableStateOf(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var isAccountLoaded by mutableStateOf(false)
        private set

    init {
        Log.i(TAG, "Initializing ViewModel and loading stored token")
        loadStoredToken()
    }
    var errorMessage: String? by mutableStateOf(null)
        internal set

    private var hasInitialized = false

    private fun loadStoredToken() {
        val savedToken = TokenManager.getToken(context)
        token = savedToken?.let { TokenResponse(it) }

        if (!hasInitialized && savedToken != null) {
            hasInitialized = true
//            fetchCurrentUser()
        }
    }


//    fun fetchCurrentUser() {
//        viewModelScope.launch {
//            try {
//                val response = apiService.getCurrentUser()
//                if (response.isSuccessful) {
//                    user = response.body()
//                } else {
//                    errorMessage = "Failed to fetch user"
//                }
//            } catch (e: Exception) {
//                errorMessage = "Exception: ${e.localizedMessage}"
//            }
//        }
//    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val authResponse = apiService.login(User(username = username, password = password)).body()
                val rawToken = authResponse?.token

                if (rawToken.isNullOrBlank()) {
                    errorMessage = "Token is null in response"
                    isLoading = false
                    return@launch
                }

                TokenManager.saveToken(context, rawToken)
                token = TokenResponse(rawToken)

                val fetchedUser = apiService.getCurrentUser().body()
                if (fetchedUser == null) {
                    errorMessage = "Failed to fetch user"
                } else {
                    user = fetchedUser
                    getMyAccount()
                }

            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    fun getMyAccount() {
        viewModelScope.launch {
            isAccountLoaded = false
            try {
                val response = apiService.getCurrentUser()
                if (response.isSuccessful) {}
            } catch (e: Exception) {
                Log.e("GetCurrentUser", "Failed to fetch account: ${e.message}")
            } finally {
                isAccountLoaded = true
            }
        }
    }
    fun logout() {
        TokenManager.clearToken(context)
        resetState()
        Log.d("Logout", "Logged out and state reset")
    }
    private fun resetState() {
        token = null
        user = null
        nbkidsAccounts = emptyList()
        errorMessage = null
    }

    fun register(
        name: String,
        phone: String,
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.register(
                    RegisterRequest(
                        username = username,
                        password = password,
                        phoneNumber = phone,
                        name = name
                    )
                )

                val registerResponse = response.body()
                Log.d("AuthViewModel", "Register response: $registerResponse")

                val tokenValue = registerResponse?.token
                if (tokenValue.isNullOrBlank()) {
                    onError("Token missing after registration")
                    return@launch
                }

                TokenManager.saveToken(context, tokenValue)
                token = TokenResponse(tokenValue)

                onSuccess()

            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            }
        }
    }
}
