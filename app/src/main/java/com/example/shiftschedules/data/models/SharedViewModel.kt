package com.example.shiftschedules.data.models

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class SharedViewModel : ViewModel() {
    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri: State<Uri?> = _selectedImageUri
    val selectedPdfUri = mutableStateOf<Uri?>(null)   // New for PDF processing

    fun setSelectedImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }
}
