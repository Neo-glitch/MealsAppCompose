package com.neo.mealsapp.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// file to hold all response class
data class MealsCategoriesResponse(
    val categories: List<MealResponse>
)

data class MealResponse(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryDescription") val description: String,
    @SerializedName("strCategoryThumb") val imageUrl: String
) {

}