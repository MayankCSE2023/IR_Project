package com.example.ui.screens


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import androidx.compose.runtime.*
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import java.io.InputStream


@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Set the background color of the screen

        Image(
            painter = painterResource(id = R.drawable.food1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Add your content here
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to MyApp", color = Color.Black)
            ImageUploader()
        }
    }
}




@Composable
fun ImageUploader() {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri ->
            // Load the image bitmap from the URI
            val contentResolver: ContentResolver = context.contentResolver
            contentResolver.openInputStream(imageUri)?.use { inputStream: InputStream ->
                val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)
                bitmap?.let {
                    imageBitmap = it.asImageBitmap()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { getContent.launch("image/*") }) {
            Text("Select Image")
        }
        imageBitmap?.let {
            Image(bitmap = it, contentDescription = "Uploaded Image", modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WelcomeScreen()
}