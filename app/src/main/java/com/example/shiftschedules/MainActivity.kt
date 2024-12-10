package com.example.shiftschedules

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.shiftschedules.ui.theme.ShiftSchedulesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftSchedulesTheme {
                MainApplication()
            }
        }
    }
}
