package com.example.shiftschedules.ui.screens

import android.util.Log
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
    var tableData by remember { mutableStateOf<List<List<String>>>(emptyList()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (selectedPdfUri != null) {
        LaunchedEffect(selectedPdfUri) {
            coroutineScope.launch {
                tableData = OCRUtils.extractTableFromPDF(context, selectedPdfUri)
            }
        }
    }

    ScrollableScreen {
        if (tableData.isNotEmpty()) {
            tableData.forEach { row ->
                Text(
                    text = row.joinToString(""), // Display each row with columns separated by ','
                    modifier = Modifier.padding(8.dp)
                )
            }
        } else {
            Text(
                text = "No table data extracted yet.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}