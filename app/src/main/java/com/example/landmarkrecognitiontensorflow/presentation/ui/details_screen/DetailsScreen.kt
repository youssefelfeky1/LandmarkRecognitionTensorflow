package com.example.landmarkrecognitiontensorflow.presentation.ui.details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    landmark: String,
    viewModel: DetailsViewModel = remember { DetailsViewModel() }
) {
    LaunchedEffect(key1= true) {
        viewModel.getLandmarkHistory(landmark)
    }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = landmark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 24.dp)
        )
        Spacer(Modifier.height(16.dp))
        if (viewModel.isLoading.value){
            CircularProgressIndicator()
        }else if (viewModel.history.value.isNotEmpty()) {
            Text(
                "History:",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            StructuredHistoryDisplay(historyText = viewModel.history.value)
        } else if (viewModel.error.value.isNotEmpty()) {
            Text("Error: ${viewModel.error.value}",color = MaterialTheme.colorScheme.primary,)
        }

    }
}

@Composable
fun StructuredHistoryDisplay(historyText: String) {
    val lines = historyText.split("\n").filter { it.isNotBlank() }

    Column(modifier = Modifier.padding(16.dp)) {
        lines.forEach { line ->
            when {
                line.startsWith("#") -> { // Handle Headings
                    val heading = line.removePrefix("#").trim()
                    Text(
                        text = heading,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                line.startsWith("-") -> { // Handle Bullet Points
                    val bulletPoint = line.removePrefix("-").trim()
                    Text(text = "• $bulletPoint", modifier = Modifier.padding(start = 8.dp))
                }
                else -> { // Handle Regular Text (if any)
                    Text(text = line.trim())
                }
            }
        }
    }
}