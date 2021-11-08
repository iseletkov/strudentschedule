package ru.studyit.studentschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.repositories.CRepositoryLessons
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CViewModelLessonList
@Inject constructor(
    repositoryLessons: CRepositoryLessons
)
    : ViewModel()
{
    init {
        viewModelScope.launch {
            repositoryLessons.getAllLessons()
        }



    }
    //LiveData позволяет отслеживать изменения в списке уроков в БД.
    val lessons = repositoryLessons.getAllFlow().asLiveData()

//    private suspend fun createInitialData(daoLessons : IDAOLessons) = withContext(Dispatchers.IO)
//    {
//        if (daoLessons.getAll().isNotEmpty())
//            return@withContext
//
//        lessons.add(CLesson(UUID.fromString("b75d453c-96f6-48a0-9619-15dc70590d59"), "Математика", LocalDateTime.parse("2021-09-30 08:00",formatter)))
//        lessons.add(CLesson(UUID.fromString("3f1a4f26-d983-4b59-a4a7-ebc59b68a34a"), "Численные методы", LocalDateTime.parse("2021-09-30 09:45",formatter)))
//        lessons.add(CLesson(UUID.fromString("85fae227-4459-4897-bb53-c63bf95f6b1c"), "Физкультура", LocalDateTime.parse("2021-09-30 11:30",formatter)))
//
//        lessons.forEach { lesson ->
//            daoLessons.insert(lesson)
//
//
//        }
//    }
}