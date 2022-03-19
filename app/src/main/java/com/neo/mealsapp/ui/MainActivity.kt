package com.neo.mealsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.neo.mealsapp.ui.details.MealDetailsViewModel
import com.neo.mealsapp.ui.details.MealsDetailsScreen
import com.neo.mealsapp.ui.meals.MealsCategoriesScreen
import com.neo.mealsapp.ui.theme.MealsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealsAppTheme {
//                MealsCategoriesScreen()
                FoodiesApp()
            }
        }
    }
}

// the actual composable app, and our nav host
@Composable
private fun FoodiesApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "destination_meals_list") {
        composable(route = "destination_meals_list") {
            MealsCategoriesScreen() { navigationMealId ->
                navController.navigate("destination_meal_details/$navigationMealId") // navigate with arg
            }
        }

        composable(route = "destination_meal_details/{meal_category_id}",
            arguments = listOf(navArgument("meal_category_id") {
                type = NavType.StringType
            })
        ) {
            val viewModel: MealDetailsViewModel = viewModel()
            MealsDetailsScreen(meal = viewModel.mealState.value)
        }

    }
}