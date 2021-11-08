package ru.studyit.studentschedule.util.rest

import ru.studyit.studentschedule.model.CLesson
import javax.inject.Inject

class CDataSourceLessons
@Inject constructor(
    private val apiTemplate: IServerAPITemplate
    )
{
    suspend fun getAllLessons() : List<CLesson>
    {
        return apiTemplate.getAllLessons()
    }

}