package com.neo.mealsapp.model

import com.neo.mealsapp.model.api.MealsWebService
import com.neo.mealsapp.model.response.MealResponse
import com.neo.mealsapp.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MealsRepository(private val webService: MealsWebService = MealsWebService()) {

    private var cachedMeals = listOf<MealResponse>()

    // accepts other function (successCallback) as a param and runs fun where needed
    // without coroutines and just callbacks
//    fun getMeals(successCallback: (response: MealsCategoriesResponse?) -> Unit){
//        return webService.getMeals().
//                enqueue(object : Callback<MealsCategoriesResponse> {
//                    override fun onResponse(
//                        call: Call<MealsCategoriesResponse>,
//                        response: Response<MealsCategoriesResponse>
//                    ) {
//                        if(response.isSuccessful)
//                            successCallback(response.body())
//                    }
//
//                    override fun onFailure(call: Call<MealsCategoriesResponse>, t: Throwable) {
//                        TODO("Treat failure")
//                    }
//                })
//    }


    // using coroutines
    suspend fun getMeals(): MealsCategoriesResponse{
        val response = webService.getMeals();
        cachedMeals = response.categories
        return response
    }

    fun getMeal(id:String) : MealResponse?{
        // ret first element that matches condition else null
       return cachedMeals.firstOrNull {
            it.id == id
        }
    }

    // implement singleton
    companion object{
        @Volatile
        private var instance: MealsRepository? = null

        fun getInstance() = instance ?: synchronized(this){
            instance ?: MealsRepository().also { instance = it }
        }
    }
}