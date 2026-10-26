package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.ScholarGold
import com.example.viewmodel.AppNavTab
import com.example.viewmodel.KarmaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: KarmaViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(
                                        text = "Karma Learning Classes",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "CSIR-NET Chemical Science • हिंदी + English",
                                        fontSize = 11.sp,
                                        color = ScholarGold
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { viewModel.setTab(AppNavTab.AI_PLANNER) },
                                    modifier = Modifier.testTag("top_bar_ai_btn")
                                ) {
                                    Icon(
                                        Icons.Default.AutoAwesome,
                                        contentDescription = "AI Planner",
                                        tint = ScholarGold
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = NavyPrimary,
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier.testTag("bottom_nav_bar")
                        ) {
                            NavigationBarItem(
                                selected = uiState.currentTab == AppNavTab.LIBRARY,
                                onClick = { viewModel.setTab(AppNavTab.LIBRARY) },
                                icon = { Icon(Icons.Default.MenuBook, contentDescription = "Library") },
                                label = { Text("Library") },
                                modifier = Modifier.testTag("tab_library")
                            )
                            NavigationBarItem(
                                selected = uiState.currentTab == AppNavTab.MCQ,
                                onClick = { viewModel.setTab(AppNavTab.MCQ) },
                                icon = { Icon(Icons.Default.Quiz, contentDescription = "MCQs") },
                                label = { Text("MCQs") },
                                modifier = Modifier.testTag("tab_mcq")
                            )
                            NavigationBarItem(
                                selected = uiState.currentTab == AppNavTab.FORMULAS,
                                onClick = { viewModel.setTab(AppNavTab.FORMULAS) },
                                icon = { Icon(Icons.Default.Functions, contentDescription = "Formulas") },
                                label = { Text("Formulas") },
                                modifier = Modifier.testTag("tab_formulas")
                            )
                            NavigationBarItem(
                                selected = uiState.currentTab == AppNavTab.AI_PLANNER,
                                onClick = { viewModel.setTab(AppNavTab.AI_PLANNER) },
                                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Planner") },
                                label = { Text("AI Guide") },
                                modifier = Modifier.testTag("tab_ai_planner")
                            )
                            NavigationBarItem(
                                selected = uiState.currentTab == AppNavTab.RESOURCES,
                                onClick = { viewModel.setTab(AppNavTab.RESOURCES) },
                                icon = { Icon(Icons.Default.PlayCircle, contentDescription = "Lectures") },
                                label = { Text("Lectures") },
                                modifier = Modifier.testTag("tab_resources")
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentTab) {
                            AppNavTab.LIBRARY -> LibraryScreen(viewModel = viewModel)
                            AppNavTab.MCQ -> McqScreen(viewModel = viewModel)
                            AppNavTab.FORMULAS -> FormulaScreen(viewModel = viewModel)
                            AppNavTab.AI_PLANNER -> AiPlannerScreen(viewModel = viewModel)
                            AppNavTab.RESOURCES -> ResourcesScreen()
                        }
                    }
                }
            }
        }
    }
}
