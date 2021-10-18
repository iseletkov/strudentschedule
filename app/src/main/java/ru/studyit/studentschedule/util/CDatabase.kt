package ru.studyit.studentschedule.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.util.converters.CConverters

@Database(
    entities = [CLesson::class],
    version = 1
)
@TypeConverters(CConverters::class)
abstract class CDatabase : RoomDatabase() {
    abstract fun daoLessons(): IDAOLessons

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CDatabase? = null

        fun getDatabase(context: Context): CDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CDatabase::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
