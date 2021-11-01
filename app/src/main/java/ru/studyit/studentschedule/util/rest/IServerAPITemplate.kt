package ru.studyit.studentschedule.util.rest

import retrofit2.http.*
import ru.studyit.studentschedule.model.CLesson
import java.util.*

interface IServerAPITemplate {
    @GET("/lessons")
    suspend fun getAllLessons(): List<CLesson>


    @POST("/lessons")
    suspend fun saveLesson(@Body lesson: CLesson)

    @DELETE("/lessons")
    suspend fun deleteLesson(@Query(value="id") id: UUID)
}