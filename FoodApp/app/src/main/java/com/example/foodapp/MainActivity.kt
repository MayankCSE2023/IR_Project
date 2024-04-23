package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import org.tensorflow.lite.TensorFlowLiteModel

class MainActivity : AppCompatActivity() {
    private lateinit var model: TensorFlowLiteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load your .tflite model from the 'ml' package
        model = TensorFlowLiteModel.create(this, "ml/model.tflite")

        setContent {
            ImagePicker { imageFile ->
                ImageClassification(model, imageFile)
            }
        }
    }
}

