package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChemistryRepository
import com.example.model.VideoResource
import com.example.ui.theme.*

@Composable
fun ResourcesScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val videos = ChemistryRepository.videoPlaylists

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Hero Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NavyDark),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null, tint = ScholarGold, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Karma Learning Classes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "CSIR-NET Chemical Science & State SET Preparation Portal. Comprehensive video lectures, bilingual concepts, and previous year paper solutions.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CSIR-NET Exam Pattern Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "CSIR-NET Chemical Science Exam Pattern",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = NavyPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExamPatternRow("Part A (General)", "20 Qs", "Attempt max 15 (2M each)", "-0.50 neg")
                ExamPatternRow("Part B (Subject Core)", "40 Qs", "Attempt max 35 (2M each)", "-0.50 neg")
                ExamPatternRow("Part C (Analytical)", "60 Qs", "Attempt max 25 (4M each)", "-1.00 neg")
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Total Marks: 200 • Total Time: 3 Hours",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = AzureSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Video Lectures & Playlists / वीडियो कक्षाएँ:",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Slate700
        )
        Text(
            "Tap any playlist to watch detailed Hindi & English explanations on YouTube.",
            fontSize = 12.sp,
            color = Slate500
        )

        Spacer(modifier = Modifier.height(10.dp))

        videos.forEach { video ->
            VideoResourceCard(
                video = video,
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Could not open video link: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contact & Support Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate100),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = AzureSecondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Contact & Guidance:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Slate900)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Email: karmalearningclasses@gmail.com",
                    fontSize = 12.5.sp,
                    color = Slate700,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Support for CSIR-NET JRF/LS, GATE Chemistry, CGSET and State SET aspirants.",
                    fontSize = 11.5.sp,
                    color = Slate500
                )
            }
        }
    }
}

@Composable
fun ExamPatternRow(part: String, total: String, attempt: String, negative: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.2f)) {
            Text(part, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Slate900)
            Text(total, fontSize = 11.sp, color = Slate500)
        }
        Column(modifier = Modifier.weight(1.5f)) {
            Text(attempt, fontSize = 12.sp, color = Slate700)
        }
        Text(negative, fontSize = 11.5.sp, color = WrongRed, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun VideoResourceCard(
    video: VideoResource,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("video_card_${video.subject}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFFFEBEE),
                shape = CircleShape,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.PlayCircle,
                        contentDescription = "Play",
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = video.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        color = AzureLight,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = video.badge,
                            color = NavyPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = video.description,
                    fontSize = 12.sp,
                    color = Slate500,
                    maxLines = 2
                )
            }
        }
    }
}
