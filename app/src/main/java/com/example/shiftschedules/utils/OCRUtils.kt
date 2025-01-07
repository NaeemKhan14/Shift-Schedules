package com.example.shiftschedules.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

object OCRUtils {
    // Convert URI to Bitmap
    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        val resolver: ContentResolver = context.contentResolver
        return try {
            MediaStore.Images.Media.getBitmap(resolver, uri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Extract text from the image using ML Kit Text Recognition
    suspend fun extractTextFromImage(bitmap: Bitmap): String {
        val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage = InputImage.fromBitmap(bitmap, 0)

        return try {
            val result = textRecognizer.process(inputImage).await()
            result.text // Return the extracted text
        } catch (e: Exception) {
            e.printStackTrace()
            "Error extracting text" // Return error message if extraction fails
        }
    }

    // Parse the extracted text into a structured format (list of lists for rows and columns)
    fun parseTabularData(extractedText: String): List<List<String>> {
        val rows = extractedText.split("\n") // Split the text into rows based on newline
        return rows.map { row ->
            row.split("\\s+".toRegex()) // Split the row into columns based on spaces
        }
    }
}
