package com.neo.mealsapp.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.neo.mealsapp.model.MealsRepository
import com.neo.mealsapp.model.response.MealResponse

class MealDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    private val repository: MealsRepository = MealsRepository.getInstance()
    // stores state of meal being displayed
    var mealState = mutableStateOf<MealResponse?>(null)


    init {
        // gets string arg with that key
        val mealId = savedStateHandle.get<String>("meal_category_id") ?: ""

        mealState.value = repository.getMeal(mealId)
    }
}