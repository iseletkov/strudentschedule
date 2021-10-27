package ru.studyit.studentschedule.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.studyit.studentschedule.model.CLesson
import java.util.*


@Dao
interface IDAOLessons {
    @Query("SELECT * FROM lessons")
    suspend fun getAll(): List<CLesson>

    @Query("SELECT * FROM lessons WHERE subject not like \"\"")
    fun getAllFlow(): Flow<List<CLesson>>


    @Query("SELECT * FROM lessons WHERE id = :id1")
    suspend fun findById(id1: UUID): CLesson?

    @Insert
    suspend fun insert(lesson: CLesson)

    @Update
    suspend fun update(lesson: CLesson)

    @Delete
    suspend fun delete(lesson: CLesson)

}

