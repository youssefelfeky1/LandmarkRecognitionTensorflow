package com.example.landmarkrecognitiontensorflow.navigation

sealed class AppScreen(val route: String) {
     object RecognitionScreen : AppScreen("RecognitionScreen")
     object DetailsScreen : AppScreen("DetailsScreen")
}