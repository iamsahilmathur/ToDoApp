package com.example.todo.domain.repositories

import com.example.todo.domain.models.EventModel

interface EventRepository {
    fun allEvents() : List<EventModel>
    suspend fun addEvent(eventModel: EventModel): Long
    suspend fun updateEvent(eventModel: EventModel): Int
    suspend fun deleteEvent(eventModel: EventModel): Int
}