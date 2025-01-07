package com.example.shiftschedules.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.shiftschedules.data.models.SharedViewModel
import com.example.shiftschedules.utils.OCRUtils
import com.example.shiftschedules.utils.ScrollableScreen
import kotlinx.coroutines.launch

@Composable
fun CameraScreen(sharedViewModel: SharedViewModel) {
    val selectedImageUri = sharedViewModel.selectedImageUri.value
    var extractedText by remember { mutableStateOf("") }
    var tabularData by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Recognize text when image is selected
    if (selectedImageUri != null) {
        LaunchedEffect(selectedImageUri) {
            val bitmap = OCRUtils.getBitmapFromUri(context, selectedImageUri)
            if (bitmap != null) {
                coroutineScope.launch {
                    extractedText = OCRUtils.extractTextFromImage(bitmap)
                    tabularData = OCRUtils.parseTabularData(extractedText)
                }
            }
        }
    }

    // Display extracted tabular data
    ScrollableScreen {
        if (tabularData.isNotEmpty()) {
            tabularData.forEach { row ->
                Text(text = row.joinToString(" | ")) // Display each row with columns separated by '|'
            }
        } else {
            Text(text = "No text extracted yet.")
        }
    }
}
