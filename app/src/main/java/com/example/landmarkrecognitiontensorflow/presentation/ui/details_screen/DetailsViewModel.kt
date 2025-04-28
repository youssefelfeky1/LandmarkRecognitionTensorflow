package com.example.landmarkrecognitiontensorflow.presentation.ui.details_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {

    val history = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf("")


    private val apiKey = "Enter your api key"
    private val model = GenerativeModel(modelName = "gemini-2.0-flash", apiKey = apiKey)



    fun getLandmarkHistory(landmark: String) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = ""
            history.value = ""
            try {
                val prompt = "Provide a concise yet informative history of $landmark. Structure your response using clear headings for different periods or significant aspects of its history. Mark each heading with a '#' symbol at the beginning. Within each section, present key events or facts as bullet points, starting each bullet point with a '-'. If specific dates are relevant to a bullet point, include them in parentheses at the beginning of the point. Aim for clarity and avoid overly complex formatting."
                val response = model.generateContent(prompt)
                history.value = response.text ?: "No information found."
            } catch (e: Exception) {
                error.value = "Failed to get history: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }
}