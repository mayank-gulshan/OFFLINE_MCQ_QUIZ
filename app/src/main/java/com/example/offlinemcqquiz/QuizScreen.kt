package com.example.offlinemcqquiz

import android.app.Activity
import com.example.offlinemcqquiz.viewmodel.QuizViewModel
import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinemcqquiz.data.Question
import com.example.offlinemcqquiz.utils.getResultMessage
@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel(), modifier: Modifier = Modifier) {

    val questions = viewModel.questions

    if (questions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.White)
        }
        return
    }

    // ✅ SHOW RESULT FIRST (important)
    if (viewModel.showResult) {

        val activity = LocalContext.current

        AlertDialog(
            onDismissRequest = {},
            containerColor = Color(0xFF1E1E2E),
            shape = RoundedCornerShape(20.dp),

            title = {
                Text(
                    text = "🎉 Quiz Completed",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },

            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "${viewModel.score} / ${viewModel.questions.size}",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6C63FF)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = getResultMessage(
                            viewModel.score,
                            viewModel.questions.size
                        ),
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                }
            },

            confirmButton = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // 🔴 Exit Button
                    Button(
                        onClick = {
                            (activity as? Activity)?.finish()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF5350)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Exit")
                    }

                    // 🔵 Restart Button
                    Button(
                        onClick = {
                            viewModel.resetQuiz()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C63FF)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Restart")
                    }
                }
            }
        )
    }

    val question = questions[viewModel.currentQuestionIndex]

    val animatedProgress by animateFloatAsState(
        targetValue = viewModel.progress,
        animationSpec = tween(500),
        label = "progressAnim"
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF5F2C82), Color(0xFF49A09D))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${viewModel.timeLeft}s", color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = Color.White
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Grammar",
            color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = question.question,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        question.options.forEachIndexed { index, option ->

            val isSelected = viewModel.selectedAnswer == index

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.05f else 1f,
                animationSpec = spring(),
                label = "scaleAnim"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .scale(scale)
                    .clickable { viewModel.selectAnswer(index) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF6C63FF) else Color.White
                )
            ) {
                Text(
                    text = "${('A' + index)}) $option",
                    modifier = Modifier.padding(16.dp),
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }

        Spacer(Modifier.weight(1f))

        val buttonAlpha by animateFloatAsState(
            targetValue = if (viewModel.selectedAnswer != -1) 1f else 0.5f,
            label = "buttonAnim"
        )

        Button(
            onClick = { viewModel.nextQuestion() },
            enabled = viewModel.selectedAnswer != -1,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(buttonAlpha)
        ) {
            Text("Next")
        }
    }
}
@Preview(showBackground = true)
@Composable

fun QuizScreen(
    viewModel: QuizViewModel = viewModel(),
    previewQuestions: List<Question>? = null
) {

    val questions = previewQuestions ?: viewModel.questions
}
