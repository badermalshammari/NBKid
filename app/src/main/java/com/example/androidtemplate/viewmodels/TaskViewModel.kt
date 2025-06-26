package com.example.androidtemplate.viewmodels

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.CreateTaskRequest
import com.example.androidtemplate.data.dtos.KidTaskDto
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.launch

class TaskViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)

    var tasks: List<KidTaskDto> by mutableStateOf(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var selectedTask: KidTaskDto? by mutableStateOf(null)

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchTasks(childId: Long) {
        viewModelScope.launch {
            isLoading = true
            try {
                tasks = apiService.getTasksForChild(childId)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message
                tasks = emptyList() // fallback
            } finally {
                isLoading = false
            }
        }
    }
    fun createTask(
        request: CreateTaskRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.createTask(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}