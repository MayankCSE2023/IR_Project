package com.example.foodapp


import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodapp.data.ExcelParser.getFoodDetails
import com.example.foodapp.screens.FoodDetailsScreen

fun NavGraphBuilder.FoodDetails(navController: NavController) {
    composable(
        route = "foodDetails/{foodName}",
        arguments = listOf(
            navArgument("foodName") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val foodName = backStackEntry.arguments?.getString("foodName") ?: ""
        val foodDetails = getFoodDetails(LocalContext.current, foodName)
            if (foodDetails != null) {
                FoodDetailsScreen(foodDetails)
            } else {
                // Handle the case where foodDetails is null, e.g., show an error message
            }
    }
}