package ru.studyit.studentschedule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.studyit.studentschedule.model.CLesson
import java.util.*


@Dao
interface IDAOLessons {
    @Query("SELECT * FROM CLesson")
    fun getAll(): List<CLesson>

    @Query("SELECT * FROM CLesson WHERE id = :id1")
    fun findById(id1: UUID): CLesson

    @Insert
    fun insert(lesson: CLesson)

    @Delete
    fun delete(lesson: CLesson)

}

