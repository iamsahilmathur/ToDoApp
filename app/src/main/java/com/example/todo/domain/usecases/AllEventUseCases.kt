package com.example.todo.domain.usecases

import com.example.todo.domain.repositories.EventRepository
import javax.inject.Inject

class AllEventUseCases @Inject constructor(private val eventRepository: EventRepository) {
    fun getAllEvents() = eventRepository.allEvents()
}
