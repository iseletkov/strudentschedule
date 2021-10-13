package ru.studyit.studentschedule.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDateTime
import java.util.*

@Entity(tableName = "lessons")
class CLesson (
    @PrimaryKey
    var id : UUID,
    @ColumnInfo(name = "subject")
    var subject : String,
    @ColumnInfo(name = "date_time")
    var dateTime : LocalDateTime
)