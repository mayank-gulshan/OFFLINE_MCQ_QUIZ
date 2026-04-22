package com.example.offlinemcqquiz.data

class QuestionRepository(private val dao: QuestionDao) {

    val questions = dao.getAllQuestions()

    suspend fun insertSampleData() {
        val sample = listOf(
            Question(1, "What is the capital of India?", listOf("Mumbai", "Delhi", "Kolkata", "Chennai"), 1),
            Question(2, "Who is known as the Father of the Nation (India)?", listOf("Nehru", "Gandhi", "Subhash Bose", "Patel"), 1),
            Question(3, "Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Saturn"), 1),
            Question(4, "Who invented the light bulb?", listOf("Newton", "Einstein", "Edison", "Tesla"), 2),
            Question(5, "Which is the largest ocean in the world?", listOf("Atlantic", "Indian", "Pacific", "Arctic"), 2)
        )
        for (q in sample) {
            dao.insert(q)
        }}
}