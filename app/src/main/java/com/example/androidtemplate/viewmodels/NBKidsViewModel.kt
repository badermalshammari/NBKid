package com.example.androidtemplate.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.User
import com.example.androidtemplate.data.requests.AuthResponse
import com.example.androidtemplate.data.requests.RegisterRequest
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import com.example.androidtemplate.utils.TokenManager
import kotlinx.coroutines.launch

class NBKidsViewModel(
    private val context: Context,
    private val apiService: ApiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)
) : ViewModel() {

    private val TAG = "AuthViewModel"

    var token: String? by mutableStateOf(null)
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
    var errorMessage: String? by mutableStateOf(null)
        internal set

    private var hasInitialized = false

    init {
        Log.i(TAG, "Initializing ViewModel and loading stored token")
        loadStoredToken()
    }

    private fun loadStoredToken() {
        val savedToken = TokenManager.getToken(context)
        token = savedToken

        if (!hasInitialized && savedToken != null) {
            hasInitialized = true
            fetchCurrentUser()
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val response = apiService.getCurrentUser()
                if (response.isSuccessful) {
                    user = response.body()
                } else {
                    errorMessage = "Failed to fetch user"
                }
            } catch (e: Exception) {
                errorMessage = "Exception: ${e.localizedMessage}"
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.login(User(username = username, password = password))
                if (!response.isSuccessful) {
                    errorMessage = "Login failed: ${response.code()} ${response.message()}"
                    isLoading = false
                    return@launch
                }

                val authResponse = response.body()
                val rawToken = authResponse?.token

                if (rawToken.isNullOrBlank()) {
                    errorMessage = "Token is null in response"
                    isLoading = false
                    return@launch
                }

                // ✅ Save token
                TokenManager.saveToken(context, rawToken)
                token = rawToken

                // ✅ Set user based on backend response
                val parent = authResponse.parent
                user = User(
                    username = parent.username,
                    password = password, // optional: you can clear it if not needed
                    id = null,
                    token = rawToken
                )

                // ✅ Optionally load extra info
                getMyAccount()

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
                if (response.isSuccessful) {
                    user = response.body()
                }
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
                token = tokenValue

                // Set user from register response
                user = User(
                    username = registerResponse.parent.username,
                    password = password,
                    id = null,
                    token = tokenValue
                )

                onSuccess()

            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            }
        }
    }
}