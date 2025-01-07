package com.example.shiftschedules.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
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
    fun extractTextFromPDF(context: Context, uri: Uri): String {

        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val document = PDDocument.load(inputStream)

            // Use PDFTextStripper to extract text from the document
            val pdfStripper = PDFTextStripper()
            val extractedText = pdfStripper.getText(document)

            document.close()
            extractedText
        } catch (e: Exception) {
            e.printStackTrace()
            "Error extracting text from PDF"
        }
    }
}
