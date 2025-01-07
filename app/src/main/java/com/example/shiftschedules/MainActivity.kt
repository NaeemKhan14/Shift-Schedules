package com.example.shiftschedules

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.shiftschedules.ui.theme.ShiftSchedulesTheme
import com.example.shiftschedules.utils.OCRUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OCRUtils.initializePDFBox(this)

        setContent {
            ShiftSchedulesTheme {
                MainApplication()
            }
        }
    }
}
