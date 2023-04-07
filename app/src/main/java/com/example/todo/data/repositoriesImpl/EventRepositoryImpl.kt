package com.example.todo.data.repositoriesImpl

import com.example.todo.data.datasource.local.daos.EvntDao
import com.example.todo.domain.models.EventModel
import com.example.todo.domain.repositories.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val eventDao: EvntDao) : EventRepository {
    override fun allEvents(): List<EventModel> {
        return eventDao.allEvents().map { it.toEventModel() }
    }

    override suspend fun addEvent(eventModel: EventModel) = eventDao.insert(eventModel.toEvent())

    override suspend fun updateEvent(eventModel: EventModel) = eventDao.update(eventModel.toEvent())

    override suspend fun deleteEvent(eventModel: EventModel) = eventDao.delete(eventModel.toEvent())

}