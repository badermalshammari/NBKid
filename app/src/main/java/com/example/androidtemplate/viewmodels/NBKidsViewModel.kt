package com.example.androidtemplate.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.Child
import com.example.androidtemplate.data.dtos.User
import com.example.androidtemplate.data.requests.RegisterRequest
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import com.example.androidtemplate.utils.TokenManager
import kotlinx.coroutines.launch

class NBKidsViewModel(
    private val context: Context,
    private val apiService: ApiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)
) : ViewModel() {

    private val TAG = "NBKidsViewModel"

    // =======================
    // UI State
    // =======================
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage: String? by mutableStateOf(null)
        internal set

    var isAccountLoaded by mutableStateOf(false)
        private set

    // =======================
    // Auth State
    // =======================
    var token: String? by mutableStateOf(null)
        private set

    var user: User? by mutableStateOf(null)
        private set

    var nbkidsAccounts: List<User> by mutableStateOf(emptyList())
        private set

    // =======================
    // Children State
    // =======================
    var children: List<Child> by mutableStateOf(emptyList())
        private set

    var selectedChild: Child? by mutableStateOf(null)

    private var hasInitialized = false

    init {
        Log.i(TAG, "Initializing ViewModel and loading stored token")
        loadStoredToken()
    }

    // =======================
    // Token Management
    // =======================
    private fun loadStoredToken() {
        val savedToken = TokenManager.getToken(context)
        token = savedToken

        if (!hasInitialized && savedToken != null) {
            hasInitialized = true
            fetchCurrentUser()
        }
    }

    fun logout() {
        TokenManager.clearToken(context)
        resetState()
        Log.d(TAG, "Logged out and state reset")
    }

    private fun resetState() {
        token = null
        user = null
        nbkidsAccounts = emptyList()
        children = emptyList()
        selectedChild = null
        errorMessage = null
    }

    // =======================
    // Auth Functions
    // =======================
    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.login(User(username = username, password = password))
                if (!response.isSuccessful) {
                    errorMessage = "Login failed: ${response.code()} ${response.message()}"
                    return@launch
                }

                val authResponse = response.body()
                val rawToken = authResponse?.token.orEmpty()

                if (rawToken.isBlank()) {
                    errorMessage = "Token is null in response"
                    return@launch
                }

                TokenManager.saveToken(context, rawToken)
                token = rawToken

                val parent = authResponse?.parent
                user = User(
                    username = parent?.username ?: "",
                    password = password,
                    token = rawToken
                )

                getMyAccount()
            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.message}"
            } finally {
                isLoading = false
            }
        }
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
                val tokenValue = registerResponse?.token.orEmpty()

                if (tokenValue.isBlank()) {
                    onError("Token missing after registration")
                    return@launch
                }

                TokenManager.saveToken(context, tokenValue)
                token = tokenValue

                user = User(
                    username = registerResponse?.parent?.username ?: "",
                    password = password,
                    token = tokenValue
                )

                onSuccess()
            } catch (e: Exception) {
                onError("Registration failed: ${e.message}")
            }
        }
    }

    // =======================
    // User Info
    // =======================
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

    fun getMyAccount() {
        viewModelScope.launch {
            isAccountLoaded = false
            try {
                val response = apiService.getCurrentUser()
                if (response.isSuccessful) {
                    user = response.body()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch account: ${e.message}")
            } finally {
                isAccountLoaded = true
            }
        }
    }

    // =======================
    // Children Management
    // =======================
    fun fetchChildren() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = apiService.getChildren()
                if (response.isSuccessful) {
                    children = response.body() ?: emptyList()
                } else {
                    errorMessage = "Failed to fetch children"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}