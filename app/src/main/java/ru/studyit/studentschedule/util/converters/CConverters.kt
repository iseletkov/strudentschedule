package ru.studyit.studentschedule.util.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import org.joda.time.LocalDateTime
import java.util.*


class CConverters {
    @TypeConverter
    fun stringFromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        return UUID.fromString(string)
    }



    @TypeConverter
    fun stringFromLocalDateTime(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    @TypeConverter
    fun localDateTimeFromString(string: String?): LocalDateTime {
        return LocalDateTime.parse(string)
    }



}
