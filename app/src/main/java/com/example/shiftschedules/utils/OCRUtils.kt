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
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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
        val textRecognizer: TextRecognizer =
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
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

    fun initializePDFBox(context: Context) {
        PDFBoxResourceLoader.init(context)
    }

    // Extract text from PDF using ML Kit
    fun extractTableFromPDF(context: Context, uri: Uri): List<List<String>> {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("temp", ".pdf", context.cacheDir)

        try {
            // Copy the PDF from URI to a temporary file
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            // Load the PDF document
            val document = PDDocument.load(tempFile)

            // Store extracted table data
            val tableData = mutableListOf<List<Pair<Float, String>>>()
            // Extract text with positional information
            val pdfStripper = object : PDFTextStripper() {
                override fun writeString(text: String, textPositions: MutableList<com.tom_roush.pdfbox.text.TextPosition>) {
                    val row = mutableListOf<Pair<Float, String>>()
                    textPositions.forEach {
                        // Store x-coordinate and text
                        row.add(it.xDirAdj to it.unicode)
                    }
                    // Group text into rows by y-coordinate
                    tableData.add(row)
                }
            }

            pdfStripper.startPage = 0
            pdfStripper.endPage = document.numberOfPages
            pdfStripper.getText(document)
            document.close()

            // Process the extracted data into rows and columns
            val rows = mutableListOf<List<String>>()
            tableData.forEach { row ->
                // Sort row elements by x-coordinate
                val sortedRow = row.sortedBy { it.first }.map { it.second }
                rows.add(sortedRow)
            }

            return rows
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        } finally {
            tempFile.delete()
        }
    }
}
