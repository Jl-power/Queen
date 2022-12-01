package com.example.dragapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dragapp.pojo.Queen
import com.example.dragapp.utils.Converters

@TypeConverters(Converters::class)
@Database(
    entities = [Queen::class],
    version = 1
)

abstract class QueenDB : RoomDatabase() {

    abstract fun getQueenDao() : QueenDao
}