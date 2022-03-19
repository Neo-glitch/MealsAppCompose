package com.neo.mealsapp.ui.details

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.neo.mealsapp.model.response.MealResponse

@Composable
fun MealsDetailsScreen(meal: MealResponse?) {
    // for animate single component
//    var isExpanded by remember { mutableStateOf(false) }
//    // state that does animation when changing it's dp value
//    val imageSizeDp: Dp by animateDpAsState(
//        targetValue = if (isExpanded) 200.dp else 100.dp
//    )

    // for animating multiple components
    var profilePictureState by remember {
        mutableStateOf(MealProfilePictureState.Normal)
    }
    // handles transition anim from one state to another
    var transition = updateTransition(targetState = profilePictureState, label = "")
    val imageSizeDp by transition.animateDp(targetValueByState = {
        it.size  // returns this to imageSizeDp
    }, label = "")
    val color by transition.animateColor(label = "") {
        it.color
    }
    val widthSize by transition.animateDp(targetValueByState = { it.borderWith }, label = "")




    Column {
        Row() {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = CircleShape,
                border = BorderStroke(width = widthSize, color = color)
            ) {
                Image(
                    painter = rememberImagePainter(meal?.imageUrl,
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSizeDp)
                        .padding(8.dp)
                )
            }
            Text(
                text = meal?.name ?: "default name",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Button(modifier = Modifier.padding(16.dp),
            onClick = {
                profilePictureState = if (profilePictureState == MealProfilePictureState.Normal)
                    MealProfilePictureState.Expanded
                else
                    MealProfilePictureState.Normal
            }) {
            Text("Change state of meal profile picture")
        }
    }
}


// to animate multiple states
enum class MealProfilePictureState(val color: Color, val size: Dp, val borderWith: Dp) {
    Normal(Color.Magenta, 120.dp, 8.dp),
    Expanded(Color.Green, 200.dp, 24.dp)
}