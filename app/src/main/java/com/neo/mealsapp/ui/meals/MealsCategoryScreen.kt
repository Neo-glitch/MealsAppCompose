package com.neo.mealsapp.ui.meals

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.neo.mealsapp.model.response.MealResponse
import com.neo.mealsapp.ui.theme.MealsAppTheme


/**
 * To rep the View(Composable Screen) in MVVM
 */
@Composable
fun MealsCategoriesScreen(navigationCallback: (String) -> Unit) {  // callback used pass meal response to calling screen
    // ties the VM to this composable lifecycle, which lives as long as this activity is alive
    // using this syntax
    val viewModel: MealsCategoriesViewModel = viewModel()
    val rememberedMeals =
        remember { mutableStateOf(emptyList<MealResponse>()) }

    // using callbacks and no coroutines
//    viewModel.getMeals { response ->
//        rememberedMeals.value =
//            response?.categories.orEmpty()  // gets the response passed from repo -> viewModel -> here
//    }


    // to launch coroutine in composable
//    val coroutineScope = rememberCoroutineScope()
//    LaunchedEffect(key1 = "GET_MEALS"){
//        // launchedEffect allows calls to block inside it just once, i.e first composition
//        coroutineScope.launch (Dispatchers.IO){
//            rememberedMeals.value = viewModel.getMeals()
//        }
//    }

    val meals = viewModel.mealsState.value

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(meals) { meal ->
            MealCategory(meal = meal, navigationCallback)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealsAppTheme {
        MealsCategoriesScreen({})
    }
}

@Composable
fun MealCategory(meal: MealResponse, navigationCallback: (String) -> Unit) {
    // handles state of expansion
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navigationCallback(meal.id)
            }
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            Image(
                painter = rememberImagePainter(meal.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
            ) {
                Text(text = meal.name, style = MaterialTheme.typography.h6)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = meal.description,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,  // makes text to be ... if overflowing screen
                        maxLines = if(isExpanded) 10 else 4
                    )
                }
            }
            Icon(
                imageVector = if(isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = "expand row items",
                modifier = Modifier
                    .padding(16.dp)
                    .align(if(isExpanded) Alignment.Bottom else Alignment.CenterVertically)
                    .clickable {
                        isExpanded = !isExpanded  // change expanded state
                    }
            )
        }
    }


}