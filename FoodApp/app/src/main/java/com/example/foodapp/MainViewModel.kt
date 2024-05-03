package com.example.foodapp

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.example.foodapp.ml.Model
import com.example.foodapp.screens.FoodDetailsScreen
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainViewModel : ViewModel() {
    var bitmap: Bitmap? by mutableStateOf(null)
    var resultText: String by mutableStateOf("")

    var isPredicting by mutableStateOf(false)
    var confidence by mutableStateOf(0f)

    fun onImageSelected(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    fun predictImage(context: Context) {
        bitmap?.let { bitmap ->
            isPredicting = true
            val labels = context.assets.open("labels.txt").bufferedReader().readLines()
            val model = Model.newInstance(context)

            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                .build()

            val processedImage = imageProcessor.process(tensorImage)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(processedImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            outputFeature0.forEachIndexed { index, fl ->
                if (outputFeature0[maxIdx] < fl)
                    maxIdx = index
            }

            resultText = labels[maxIdx]

            confidence = outputFeature0[maxIdx]

            isPredicting = false

            model.close()
        }
    }
}


