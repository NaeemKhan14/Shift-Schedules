package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.shiftschedules.data.models.SharedViewModel
import com.example.shiftschedules.utils.OCRUtils
import com.example.shiftschedules.utils.ScrollableScreen
import kotlinx.coroutines.launch

@Composable
fun PDFDataProcessing(sharedViewModel: SharedViewModel) {
    val selectedPdfUri = sharedViewModel.selectedPdfUri.value
    var extractedText by remember { mutableStateOf("") }
    var structuredData by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (selectedPdfUri != null) {
        LaunchedEffect(selectedPdfUri) {
            coroutineScope.launch {
                // Extract text using the updated function
                extractedText = OCRUtils.extractTextFromPDF(context, selectedPdfUri)

                // Parse the extracted text into a structured format
                structuredData = OCRUtils.parseTabularData(extractedText)
            }
        }
    }

    ScrollableScreen {
        if (structuredData.isNotEmpty()) {
            structuredData.forEach { row ->
                Text(
                    text = row.joinToString(" | "), // Display each row with columns separated by '|'
                    modifier = Modifier.padding(8.dp)
                )
            }
        } else {
            Text(
                text = if (extractedText.isEmpty()) "No text extracted yet." else extractedText,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}