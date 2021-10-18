package ru.studyit.studentschedule.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.studyit.studentschedule.model.CLesson
import java.util.*


@Dao
interface IDAOLessons {
    @Query("SELECT * FROM lessons")
    fun getAll(): LiveData<List<CLesson>>

    @Query("SELECT * FROM lessons WHERE id = :id1")
    fun findById(id1: UUID): LiveData<CLesson>

    @Insert
    fun insert(lesson: CLesson)

    @Delete
    fun delete(lesson: CLesson)

}

