package com.example.androidtemplate.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.KidTaskDto
import com.example.androidtemplate.ui.composables.YouTubeWebView

fun convertToEmbedUrl(youtubeUrl: String): String {
    return when {
        youtubeUrl.contains("embed") -> youtubeUrl
        youtubeUrl.contains("v=") -> {
            val videoId = youtubeUrl.substringAfter("v=").substringBefore("&")
            "https://www.youtube.com/embed/$videoId"
        }
        youtubeUrl.contains("youtu.be") -> {
            val videoId = youtubeUrl.substringAfterLast("/")
            "https://www.youtube.com/embed/$videoId"
        }
        else -> youtubeUrl
    }
}

@Composable
fun TaskDetail(
    task: KidTaskDto,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE8EDF3))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "In Progress",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1A3D6D))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
            Text(
                "Done",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFFFF9800))
            }
            Text("To Do Tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }


        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Task", fontSize = 15.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1A2B)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = task.description,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 28.sp,
                    color = Color(0xFF1B2C48)
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.points),
                            contentDescription = "Points",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${task.points ?: 0}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.gems),
                            contentDescription = "Gems",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${task.gems}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }


        if (task.type.uppercase() == "VIDEO" && !task.youtubeUrl.isNullOrBlank()) {
            val embedUrl = convertToEmbedUrl(task.youtubeUrl)
            Log.d("TaskDetail", "Embedding YouTube URL: $embedUrl")

            Spacer(modifier = Modifier.height(24.dp))
            Text("Watch Video", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            YouTubeWebView(url = embedUrl)
        }
    }
}