package ru.studyit.studentschedule.repositories

import kotlinx.coroutines.flow.Flow
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.util.rest.CDataSourceLessons
import javax.inject.Inject

class CRepositoryLessons
@Inject constructor(
    private val dataSourceLessons: CDataSourceLessons,
    private val daoLessons: IDAOLessons
)
{
    suspend fun getAllLessons() : List<CLesson>
    {
        val lessonsFromDB = daoLessons.getAll()
        val temp_lessons : List<CLesson>
        try {
            temp_lessons = dataSourceLessons.getAllLessons()
        }
        //Если какая-то проблема в сети
        catch(e : Exception)
        {
            //Возвращаем данные из БД.
            return lessonsFromDB
        }

        lessonsFromDB
            .filter {currentLesson ->
                return@filter !temp_lessons.contains(currentLesson)
            }
            .forEach { currentLesson ->
                //Удалять
                daoLessons.delete(currentLesson)
            }

        temp_lessons.forEach { lesson ->
            val lessonFromDB = daoLessons.findById(lesson.id)
            lessonFromDB?.let { //когда lessonFromDB не равна NULL
                daoLessons.update(lesson)
            } ?: run { //когда lessonFromDB равна NULL
                daoLessons.insert(lesson)
            }
        }
        return temp_lessons
    }

    fun getAllFlow() : Flow<List<CLesson>> = daoLessons.getAllFlow()
}