//Controls.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snake.snakes2.domain.Direction

@Composable
fun Controls(onDirectionChange: (Direction) -> Unit) { //Accept Direction
    val buttonSize = Modifier.size(60.dp) // Increase button size
    val iconSize = Modifier.size(60.dp)  // Increase icon size

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(22.dp)) {
        Button(
            onClick = { onDirectionChange(Direction.UP) },
            modifier = buttonSize,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548)),
        ){
            Icon(
                Icons.Filled.KeyboardArrowUp,
                contentDescription = "Up",
                modifier = iconSize,
                tint = Color(0xff242c11)
            )
        }
        Row {
            Button(
                onClick = { onDirectionChange(Direction.LEFT) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548))
                ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Left",
                    modifier = iconSize,
                    tint = Color(0xff242c11)
                )
            }
            Spacer(modifier = buttonSize)

            Button(
                onClick = { onDirectionChange(Direction.RIGHT) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Right",
                    modifier = iconSize,
                    tint = Color(0xff242c11)
                )
            }
        }
        Button(
            onClick = { onDirectionChange(Direction.DOWN) },
            modifier = buttonSize,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548))
        ) {
            Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = "Down",
                modifier = iconSize,
                tint = Color(0xff242c11)
            )
        }
    }
    }
