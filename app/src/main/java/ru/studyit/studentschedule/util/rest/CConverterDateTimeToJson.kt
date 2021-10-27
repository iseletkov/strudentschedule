package ru.studyit.studentschedule.util.rest

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class CConverterDateTimeToJson {
   private val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return formatter.parseLocalDateTime(value)
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.toString(formatter)
    }
}