package com.example.landmarkrecognitiontensorflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.landmarkrecognitiontensorflow.presentation.ui.details_screen.DetailsScreen
import com.example.landmarkrecognitiontensorflow.presentation.ui.recognition_screen.RecognitionScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.RecognitionScreen.route
    ){
        composable(AppScreen.RecognitionScreen.route){
            RecognitionScreen(navController= navController)
        }
        composable(
            route= AppScreen.DetailsScreen.route+"/{landmark}",
            arguments = listOf(
                navArgument("landmark") { type = NavType.StringType }
            )
        ){
            val landmark = it.arguments?.getString("landmark")!!

            DetailsScreen(landmark = landmark)
        }
    }
}