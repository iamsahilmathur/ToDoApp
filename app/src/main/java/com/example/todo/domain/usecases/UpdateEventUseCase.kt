package com.example.todo.domain.usecases


import com.example.todo.domain.models.EventModel
import com.example.todo.domain.repositories.EventRepository
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(private val eventRepository: EventRepository) {

    suspend fun updateEvent(event : EventModel) = eventRepository.updateEvent(event)
}