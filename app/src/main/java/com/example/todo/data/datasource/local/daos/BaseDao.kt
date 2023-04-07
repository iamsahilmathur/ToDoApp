package com.example.todo.data.datasource.local.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T) :Long
//    @Delete
//    suspend fun deleteAll()
    @Delete
    suspend fun delete(data: T) : Int

    @Update
    suspend fun update(data: T) : Int
}