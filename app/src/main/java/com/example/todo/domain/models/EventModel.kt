package com.example.todo.domain.models

import com.example.todo.data.datasource.local.entities.Event


data class EventModel(
    val id: Int=0,
    val eventName: String,
    val eventDesc: String
) {
    fun toEvent() = Event(
        id = id,
        eventName = eventName,
        eventDesc = eventDesc
    )
}