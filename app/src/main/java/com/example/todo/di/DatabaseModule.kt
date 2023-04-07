package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.data.datasource.local.EventDatabase
import com.example.todo.data.repositoriesImpl.EventRepositoryImpl
import com.example.todo.domain.repositories.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "event_db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): EventDatabase =
        Room.databaseBuilder(
            appContext,
            EventDatabase::class.java,
            "event_db"
        ).build()

    @Provides
    @Singleton
    fun eventDao(eventDatabase: EventDatabase) = eventDatabase.eventDao()

    @Provides
    @Singleton
    fun eventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository {
        return eventRepositoryImpl
    }

}