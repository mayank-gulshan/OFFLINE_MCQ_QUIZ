package com.example.offlinemcqquiz.utils

fun getResultMessage(score: Int, total: Int): String {
    val percent = (score * 100) / total

    return when {
        percent >= 80 -> "Excellent! 🔥"
        percent >= 50 -> "Good job 👍"
        percent >= 30 -> "Keep practicing 🙂"
        else -> "Try again 💪"
    }
}