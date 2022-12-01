package com.example.dragapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragapp.pojo.Queen

@Dao
interface QueenDao {

    @Query("SELECT * FROM Queen")
    fun getAll(): List<Queen>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(queen: Queen)
}