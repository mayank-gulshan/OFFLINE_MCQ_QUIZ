package com.example.offlinemcqquiz.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.offlinemcqquiz.data.Question
import com.example.offlinemcqquiz.data.QuestionRepository
import com.example.offlinemcqquiz.data.QuizDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = QuizDatabase.getDatabase(application).questionDao()
    private val repository = QuestionRepository(dao)

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var currentQuestionIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)
    var selectedAnswer by mutableIntStateOf(-1)
    var showResult by mutableStateOf(false)

    var timeLeft by mutableStateOf(10)
    var progress by mutableFloatStateOf(1f)

    private var timerJob: Job? = null

    private var quizStarted = false  // ✅ add this

    init {
        viewModelScope.launch {
            val current = repository.questions.first()
            if (current.isEmpty()) {
                repository.insertSampleData()
            }

            repository.questions.collect {
                questions = it
                if (questions.isNotEmpty() && !quizStarted) {
                    quizStarted = true
                    startTimer()
                }
            }
        }
    }
    private fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            timeLeft = 10
            progress = 1f

            for (i in 10 downTo 0) {

                // ✅ STOP if quiz finished
                if (showResult) return@launch

                timeLeft = i
                progress = i / 10f
                delay(1000)
            }

            // ⏱ Auto move ONLY if quiz not finished
            if (!showResult) {
                nextQuestion(auto = true)
            }
        }
    }

    fun selectAnswer(index: Int) {
        selectedAnswer = index
    }

    fun nextQuestion(auto: Boolean = false) {
        if (questions.isEmpty() || showResult) return

        timerJob?.cancel()

        // ✅ Only count score if user answered manually
        if (!auto && selectedAnswer == questions[currentQuestionIndex].correctAnswer) {
            score++
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            selectedAnswer = -1
            startTimer()
        } else {
            showResult = true
            timerJob?.cancel()   // ✅ STOP TIMER HERE
        }
    }

    fun resetQuiz() {
        timerJob?.cancel()

        currentQuestionIndex = 0
        score = 0
        selectedAnswer = -1
        showResult = false

        startTimer()
    }
}