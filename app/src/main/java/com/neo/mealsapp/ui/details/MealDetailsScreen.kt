package com.neo.mealsapp.ui.details

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.neo.mealsapp.model.response.MealResponse
import kotlin.math.min

@Composable
fun MealsDetailsScreen(meal: MealResponse?) {
    // to make column scrollable
    val scrollState = rememberScrollState()

    // scroll state value increases when scrolled
    val offset = min(
        1f,
        1 - (scrollState.value / 600f) // offset bigger when user scrolls up since -ve - -ve is +ve with max offset value of 1f
    )
    val size by animateDpAsState(
        targetValue = max(
            // 100.dp is min size image will be when we are scrolling down
            100.dp, 200.dp * offset)
    )
    Surface(color = MaterialTheme.colors.background) {
        Column {

            Surface(elevation = 4.dp) { // acts as our custom toolbar using elevation attr
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = CircleShape,
                        border = BorderStroke(width = 2.dp, color = Color.Green)
                    ) {
                        Image(
                            painter = rememberImagePainter(meal?.imageUrl,
                                builder = {
                                    transformations(CircleCropTransformation())
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(size)
                        )
                    }
                    Text(
                        text = meal?.name ?: "default name",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Column(modifier = Modifier.verticalScroll(scrollState)) {
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
                Text("This is text element", modifier = Modifier.padding(32.dp))
            }
        }
    }

}


// to animate multiple states
enum class MealProfilePictureState(val color: Color, val size: Dp, val borderWith: Dp) {
    Normal(Color.Magenta, 120.dp, 8.dp),
    Expanded(Color.Green, 200.dp, 24.dp)
}