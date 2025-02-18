//Controls.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snake.snakes2.domain.Direction //Import Direction

@Composable
fun Controls(onDirectionChange: (Direction) -> Unit) { //Accept Direction
    val buttonSize = Modifier.size(64.dp)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
        Button(onClick = { onDirectionChange(Direction.UP) }, modifier = buttonSize) {
            Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
        }
        Row {
            Button(onClick = { onDirectionChange(Direction.LEFT) }, modifier = buttonSize) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Left")
            }
            Spacer(modifier = buttonSize)
            Button(onClick = { onDirectionChange(Direction.RIGHT) }, modifier = buttonSize) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Right")
            }
        }
        Button(onClick = { onDirectionChange(Direction.DOWN) }, modifier = buttonSize) {
            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
        }
    }
}
