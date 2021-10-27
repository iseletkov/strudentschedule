package ru.studyit.studentschedule.util.rest

import retrofit2.http.GET
import ru.studyit.studentschedule.model.CLesson

interface IServerAPITemplate {
    @GET("/lessons")
    suspend fun getAllLessons(): List<CLesson>

}