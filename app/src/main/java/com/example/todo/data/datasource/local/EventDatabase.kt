package com.example.todo.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.datasource.local.daos.EvntDao
import com.example.todo.data.datasource.local.entities.Event

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase  : RoomDatabase(){

    abstract fun eventDao() : EvntDao
}