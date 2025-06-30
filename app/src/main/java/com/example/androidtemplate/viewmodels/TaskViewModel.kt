package com.example.androidtemplate.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplate.data.dtos.CreateTaskRequest
import com.example.androidtemplate.data.dtos.KidTaskDto
import com.example.androidtemplate.data.dtos.VideoOption
import com.example.androidtemplate.network.ApiService
import com.example.androidtemplate.network.RetrofitHelper
import kotlinx.coroutines.launch

class TaskViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitHelper.getInstance(context).create(ApiService::class.java)

    var tasks: List<KidTaskDto> by mutableStateOf(emptyList())
        private set

    var videoOptions: List<VideoOption> by mutableStateOf(emptyList())
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
                tasks = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                val fetched = apiService.getAllVideos()
                Log.d("FETCH_VIDEOS", "Fetched ${fetched.size} videos")
                val distinct = fetched.distinctBy { it.id }
                Log.d("FETCH_VIDEOS", "After distinct: ${distinct.size} videos")
                videoOptions = distinct
            } catch (e: Exception) {
                errorMessage = "Failed to load videos: ${e.localizedMessage}"
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

    fun completeTask(
        childId: Long,
        taskId: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.markTaskAsFinished(childId, taskId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}