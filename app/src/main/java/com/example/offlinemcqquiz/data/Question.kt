package com.example.offlinemcqquiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val question: String,
    val options:List<String>,
    val correctAnswer:Int
)

class Convertors{
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString("||")

    @TypeConverter
    fun toList(string: String): List<String> = string.split("||")
}
