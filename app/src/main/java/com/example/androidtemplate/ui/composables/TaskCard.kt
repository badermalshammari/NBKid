package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



//@Composable
//fun TaskCard(
//    taskTitle: String,
//    taskSubtitle: String? = null,
//    coinReward: Int,
//    gemReward: Int,
//    progress: Int? = null, // null means no progress bar
//    backgroundColor: Color = Color(0xFFEAF4FD) // default light blue
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp, horizontal = 16.dp)
//            .clip(RoundedCornerShape(40.dp))
//            .background(backgroundColor)
//            .padding(16.dp)
//    ) {
//        Column {
//            Text("Task Name", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
//
//            Text(
//                taskTitle,
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//
//            taskSubtitle?.let {
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(it, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
//            }
//
//            progress?.let {
//                Spacer(modifier = Modifier.height(8.dp))
//                LinearProgressIndicator(
//                    progress = it / 100f,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(10.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                    color = if (it == 100) Color(0xFF00C853) else Color(0xFF508AC9),
//                    trackColor = Color.LightGray
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    "$it%",
//                    style = MaterialTheme.typography.bodySmall,
//                    fontWeight = FontWeight.Bold,
//                    color = if (it == 100) Color(0xFF00C853) else Color(0xFF508AC9)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Filled.AttachMoney,
//                        contentDescription = null,
//                        tint = Color(0xFFFFD700) // Gold-like color for coins
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(coinReward.toString(), fontWeight = FontWeight.Bold)
//                }
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Filled.Star,
//                        contentDescription = null,
//                        tint = Color(0xFF00B8D4) // Cyan-like color for gems
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(gemReward.toString(), fontWeight = FontWeight.Bold)
//                }
//            }
//        }
//    }
//}

@Composable
fun TaskCard(
    taskTitle: String,
    taskSubtitle: String? = null,
    coinReward: Int,
    gemReward: Int,
    progress: Int? = null, // null means no progress bar
    backgroundColor: Color = Color(0xFFEAF4FD) // default light blue
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column {
            Text("Task Name", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            Text(
                taskTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            taskSubtitle?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(it, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            progress?.let {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = it / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    color = if (it == 100) Color(0xFF00C853) else Color(0xFF508AC9),
                    trackColor = Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$it%",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = if (it == 100) Color(0xFF00C853) else Color(0xFF508AC9)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Spacer(modifier = Modifier.width(1.dp)) // Push rewards to right side if needed

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AttachMoney,
                            contentDescription = null,
                            tint = Color(0xFFFFD700) // Gold-like color for coins
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(coinReward.toString(), fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFF00B8D4) // Cyan-like color for gems
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(gemReward.toString(), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
