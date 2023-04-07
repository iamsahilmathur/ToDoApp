package com.example.todo.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.domain.models.EventModel


@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "event_name")
    val eventName: String,
    @ColumnInfo(name = "event_desc")
    val eventDesc: String
) {

    fun toEventModel(): EventModel =
        EventModel(
            id = id,
            eventName = eventName,
            eventDesc = eventDesc
        )

}