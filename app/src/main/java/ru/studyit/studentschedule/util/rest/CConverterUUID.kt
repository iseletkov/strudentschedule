package ru.studyit.studentschedule.util.rest

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDateTime
import java.util.*

class CConverterUUID {
    @FromJson
    fun fromJson(value: String): UUID {
        return UUID.fromString(value)
    }

    @ToJson
    fun toJson(value: UUID): String {
        return value.toString()
    }
}