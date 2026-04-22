package com.example.offlinemcqquiz.data

class QuestionRepository(private val dao: QuestionDao) {

    val questions = dao.getAllQuestions()

    suspend fun insertSampleData() {
        val sample = listOf(
            Question(1, "Choose the correct sentence:",
                listOf("She go to school daily", "She goes to school daily", "She going to school daily", "She gone to school daily"),
                1),

            Question(2, "Fill in the blank: He ___ playing football.",
                listOf("is", "are", "am", "be"),
                0),

            Question(3, "Choose the correct past tense of 'eat':",
                listOf("eated", "ate", "eaten", "eat"),
                1),

            Question(4, "Identify the correct article: ___ apple a day keeps the doctor away.",
                listOf("A", "An", "The", "No article"),
                1),

            Question(5, "Choose the correct plural form of 'child':",
                listOf("childs", "childes", "children", "childrens"),
                2)
        )
        for (q in sample) {
            dao.insert(q)
        }}
}