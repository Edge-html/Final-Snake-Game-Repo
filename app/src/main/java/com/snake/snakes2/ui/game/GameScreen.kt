//GameScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.ui.game.Board
import com.snake.snakes2.ui.game.Controls
import com.snake.snakes2.viewmodel.GameViewModel


@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val state = gameViewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 32.dp) // Add top padding here
    ) {
        Text(
            text = "Score: ${state.value.score}",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Board(state.value)
        Controls { gameViewModel.changeDirection(it) }
    }
}



