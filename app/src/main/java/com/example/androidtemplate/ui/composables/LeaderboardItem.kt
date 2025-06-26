package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.androidtemplate.data.dtos.LeaderboardEntryDto

@Composable
fun LeaderboardItem(rank: Int, entry: LeaderboardEntryDto) {
    val borderColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(
                if (borderColor != Color.Transparent) {
                    Modifier.border(
                        width = 5.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$rank.", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(entry.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.points),
                        contentDescription = "Points",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${entry.points ?: 0}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }            }

            if (rank == 1) {
                Image(
                    painter = painterResource(id = R.drawable.crown),
                    contentDescription = "Crown",
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}