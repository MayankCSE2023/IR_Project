package com.example.foodapp

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.data.ExcelParser
import com.example.foodapp.screens.FoodDetailsScreen


class MainActivity : ComponentActivity() {

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                viewModel.onImageSelected(bitmap)
            }
        }

    private val viewModel by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
//                FoodDiscoveryApp(viewModel)
                val navController = rememberNavController()
                NavHost(navController, startDestination = "foodDiscovery") {
                    composable("foodDiscovery") {
                        FoodDiscoveryApp(navController, viewModel)
                    }
                    FoodDetails(navController) // Call FoodDetails here
                }
            }
        }
    }

    @Composable
    private fun FoodDiscoveryApp(navController: NavHostController, viewModel: MainViewModel) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(text = "Food Discovery",style = MaterialTheme.typography.headlineLarge)

            viewModel.bitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }

            viewModel.isPredicting.let { isPredicting ->
                if (isPredicting) {
                    CircularProgressIndicator()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { pickImage.launch("image/*") }) {
                Text("Select Image")
            }

            Button(onClick = { viewModel.predictImage(context) }) {
                Text("Predict")
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Text(text = viewModel.resultText)
            Text(
                text = "Prediction: ${viewModel.resultText}\nConfidence: ${String.format("%.2f", viewModel.confidence*100)}%",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(onClick = {
                // Open a new UI to display food details using foodDetails
                val foodName = viewModel.resultText
                navController.navigate("foodDetails/$foodName")
            }) {
                Text("Details")
            }
        }
    }
}