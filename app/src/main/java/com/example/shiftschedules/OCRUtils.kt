import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OCRUtils {

    // Initialize the recognizer with options
    private val recognizer: TextRecognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    // Function to process an image and extract text
    fun processImage(image: Bitmap, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        // Convert Bitmap to InputImage
        val inputImage = InputImage.fromBitmap(image, 0)

        // Process the image with the recognizer
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                // Extracted text on successful recognition
                onSuccess(visionText.text)
            }
            .addOnFailureListener { exception ->
                // Handle the error if the recognition fails
                onFailure(exception)
            }
    }

    // In case we need to close a stream in the future, we will add the logic here
    fun close() {

    }
}
