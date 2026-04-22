package com.example.offlinemcqquiz.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Question::class], version = 1, exportSchema = false)

@TypeConverters(Convertors::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}