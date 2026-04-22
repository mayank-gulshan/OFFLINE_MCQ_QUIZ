package com.example.offlinemcqquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.offlinemcqquiz.data.StartScreen
import com.example.offlinemcqquiz.ui.theme.OFFLINEMCQQUIZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OFFLINEMCQQUIZTheme {

                var started by remember { mutableStateOf(false) }

                if (!started) {
                    StartScreen(
                        onStartClick = { started = true }
                    )
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        QuizScreen(
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}