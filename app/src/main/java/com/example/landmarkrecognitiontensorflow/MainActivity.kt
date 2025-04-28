package com.example.landmarkrecognitiontensorflow

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.landmarkrecognitiontensorflow.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (! hasCameraPermission()){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),0)
            }
            AppNavigation()
        }
    }

    fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

