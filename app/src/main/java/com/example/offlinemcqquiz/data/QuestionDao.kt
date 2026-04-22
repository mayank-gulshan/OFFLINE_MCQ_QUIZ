package com.example.offlinemcqquiz.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: Question)
    @Query("SELECT * FROM questions")
     fun getAllQuestions(): Flow<List<Question>>
}

