package com.example.todo.data.datasource.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.todo.data.datasource.local.entities.Event

@Dao
interface EvntDao : BaseDao<Event> {

    @Query("SELECT * FROM events")
    fun allEvents(): List<Event>
}