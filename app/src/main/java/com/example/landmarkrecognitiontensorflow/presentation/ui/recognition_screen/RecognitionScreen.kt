package com.example.landmarkrecognitiontensorflow.presentation.ui.recognition_screen

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.landmarkrecognitiontensorflow.data.TfLiteLandmarkClassifier
import com.example.landmarkrecognitiontensorflow.domain.Classification
import com.example.landmarkrecognitiontensorflow.navigation.AppScreen
import com.example.landmarkrecognitiontensorflow.presentation.CameraPreview
import com.example.landmarkrecognitiontensorflow.presentation.LandmarkImageAnalyzer
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.landmarkrecognitiontensorflow.presentation.ui.theme.LandmarkRecognitionTensorflowTheme

@Composable
fun RecognitionScreen(modifier: Modifier = Modifier,navController: NavController) {
    val context = LocalContext.current

    LandmarkRecognitionTensorflowTheme {

        var classifications by remember {
            mutableStateOf(emptyList<Classification>())
        }
        val analyzer = remember {
            LandmarkImageAnalyzer(
                classifier = TfLiteLandmarkClassifier(
                    content = context
                ),
                onResult = {
                    classifications = it
                }
            )
        }

        val controller = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    analyzer
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )

            ImageRecognitionView()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                classifications.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(AppScreen.DetailsScreen.route + "/${it.name}")
                                },
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Landmark Details",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    navController.navigate(AppScreen.DetailsScreen.route + "/${it.name}")
                                }
                        )
                    }

                }
            }


        }
    }
}


@Composable
fun ImageRecognitionView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(321.dp)
        ) {
            drawRect(
                color = Color.Green,
                size = Size(321.dp.toPx(), 321.dp.toPx()),
                style = Stroke(width = 4f)
            )
        }

    }
}
