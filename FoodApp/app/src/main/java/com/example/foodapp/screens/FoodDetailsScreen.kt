package com.example.foodapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodapp.data.FoodDetails

@Composable
fun FoodDetailsScreen(foodDetails: FoodDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Food Details", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Name: ${foodDetails.name}")
        Text("Type: ${foodDetails.type}")
        Text("Veg/Non-Veg: ${foodDetails.vegNon}")
        Text("Description: ${foodDetails.description}")
        Text("Recommendations: ${foodDetails.recommendations}")
    }
}