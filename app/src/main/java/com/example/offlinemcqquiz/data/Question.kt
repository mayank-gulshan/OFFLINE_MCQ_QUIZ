package com.example.offlinemcqquiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val Question: String,
    val Options:List<String>,
    val CorrectAnswer:Int
)

class Convertors{
   @TypeConverter
   fun fromList(list:List<String>):String{
       return list.joinToString(",")
   }
    @TypeConverter
    fun toList(string:String):List<String>{
        return string.split(",")
   }
}
