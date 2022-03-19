package com.neo.mealsapp.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.mealsapp.model.MealsRepository
import com.neo.mealsapp.model.response.MealResponse
import com.neo.mealsapp.model.response.MealsCategoriesResponse
import kotlinx.coroutines.*

/**
 * Lives as long as the Composable since instantiated in composable
 */
class MealsCategoriesViewModel(
    private val repository: MealsRepository = MealsRepository.getInstance()  // repository instance
) : ViewModel(){

    // custom defined scope, could have just use vm scope that auto handles cancellation
    // after vm lifecycle ends
    private val mealsJob = Job()

    init {
        val scope = CoroutineScope(mealsJob + Dispatchers.IO)
        scope.launch(Dispatchers.IO){
            mealsState.value = getMeals()
        }
    }

    val mealsState = mutableStateOf(emptyList<MealResponse>())

    // using callback and no coroutines
//    // accepts a fun as param
//    fun getMeals(successCallback: (response: MealsCategoriesResponse?) -> Unit){
//        // if null, return an empty list
//        repository.getMeals {response ->
//            successCallback(response)
//        }
//    }

    private suspend fun getMeals(): List<MealResponse> {
        return repository.getMeals().categories  // gets the response, which is a list of MealResponse
    }

    override fun onCleared() {
        super.onCleared()
        // cancel job was vm is cleared
        mealsJob.cancel()
    }
}