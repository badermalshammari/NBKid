package com.example.androidtemplate.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.VideoOption
import com.example.androidtemplate.data.dtos.CreateTaskRequest
import com.example.androidtemplate.data.dtos.TaskType
import com.example.androidtemplate.ui.composables.BalanceTaskInfoComposable
import com.example.androidtemplate.ui.composables.GradientSendButton
import com.example.androidtemplate.ui.composables.LabeledInput
import com.example.androidtemplate.ui.composables.TaskTypeOption
import com.example.androidtemplate.ui.composables.YouTubeWebView
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    cardId: Long,
    cardViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    navController: NavController,
    taskViewModel: TaskViewModel,
    nbKidsViewModel: NBKidsViewModel,
) {
    val context = LocalContext.current
    val cards by cardViewModel.cards.collectAsState()
    val card = cards.find { it.cardId == cardId }

    val wallet by walletViewModel.walletState.collectAsState()
    val childid = wallet?.child?.childId

    val videoOptions = taskViewModel.videoOptions.distinctBy { it.id }
    var selectedVideo by remember { mutableStateOf<VideoOption?>(null) }
    var videoDropdownExpanded by remember { mutableStateOf(false) }
    var videoOptionsLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(childid) {
        walletViewModel.fetchWallet(childid)
        if (!videoOptionsLoaded) {
            taskViewModel.fetchVideos()
            videoOptionsLoaded = true
        }
    }

    val accountName = card?.cardHolderName ?: "N/A"
    val accountNumber = card?.accountNumber?.toString() ?: "N/A"
    val availableBalance = "${card?.balance ?: "0.000"} KD"
    val availableGems = (wallet?.gems ?: 0).toString()
    val points = (wallet?.pointsBalance ?: 0).toString()

    val todoIcon = painterResource(id = R.drawable.todolist)
    val videoIcon = painterResource(id = R.drawable.transfaremoney)

    var selectedTaskType by remember { mutableStateOf("Task To-do") }
    var task by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var gems by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = card?.cardHolderName ?: "No Name", fontWeight = FontWeight.Bold)
                        Text("(${card?.accountNumber})", style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                BalanceTaskInfoComposable(availableBalance, availableGems, points)

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TaskTypeOption(todoIcon, "Task To-do", selectedTaskType == "Task To-do") {
                        selectedTaskType = "Task To-do"
                        task = ""
                        description = ""
                        selectedVideo = null
                    }
                    TaskTypeOption(videoIcon, "Video", selectedTaskType == "Video") {
                        selectedTaskType = "Video"
                        task = ""
                        description = ""
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                LabeledInput("Task", task) { task = it }
                LabeledInput("Description", description) { description = it }
                LabeledInput("Gems", gems) { gems = it }

                if (selectedTaskType == "Video") {
                    Spacer(modifier = Modifier.height(16.dp))

                    ExposedDropdownMenuBox(
                        expanded = videoDropdownExpanded,
                        onExpandedChange = { videoDropdownExpanded = !videoDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectedVideo?.title ?: "Select Video",
                            onValueChange = {},
                            label = { Text("Select Video") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = videoDropdownExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(0.85f)
                                .height(65.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFFFFFFF),
                                unfocusedBorderColor = Color.White,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                        )

                        ExposedDropdownMenu(
                            expanded = videoDropdownExpanded,
                            onDismissRequest = { videoDropdownExpanded = false }
                        ) {
                            videoOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.title) },
                                    onClick = {
                                        selectedVideo = option
                                        task = option.title
                                        description = option.description
                                        videoDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    selectedVideo?.let {
                        if (selectedVideo?.youtubeUrl.toString() == null) {
                            Text(
                                "Error while loading the video..",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Red
                            )

                        } else {
                            val embedUrl = convertToEmbedUrl(selectedVideo?.youtubeUrl.toString())
                            Log.d("TaskDetail", "Embedding YouTube URL: $embedUrl")

                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Watch Video", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            YouTubeWebView(url = embedUrl)
                        }
                    }
                }
                    Spacer(modifier = Modifier.height(24.dp))

                GradientSendButton {
                    Log.d("TASK_DEBUG", "Send button clicked")

                    val gemsInt: Int? = gems.toIntOrNull() // ✅ Nullable

                    val parentId = nbKidsViewModel.user?.parentId
                    val childId = card?.childId

                    Log.d(
                        "TASK_DEBUG",
                        "Validation: parentId=$parentId, childId=$childId, task='$task', gems=$gemsInt"
                    )

                    if (task.isBlank()) {
                        Toast.makeText(context, "Please enter a task title", Toast.LENGTH_SHORT).show()
                        return@GradientSendButton
                    }

                    if (parentId != null && childId != null) {
                        val request = CreateTaskRequest(
                            parentId = parentId,
                            childId = childId,
                            title = task,
                            description = description,
                            type = if (selectedTaskType == "Video") TaskType.VIDEO else TaskType.TASK,
                            gems = gemsInt, // ✅ pass null if empty
                            educationalContentId = selectedVideo?.id
                        )

                        Log.d("TASK_DEBUG", "Sending request: $request")

                        taskViewModel.createTask(
                            request,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Task created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Log.e("TASK_DEBUG", "Error creating task: $it")
                                Toast.makeText(
                                    context,
                                    "Failed to create task: $it",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill in all required fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

}