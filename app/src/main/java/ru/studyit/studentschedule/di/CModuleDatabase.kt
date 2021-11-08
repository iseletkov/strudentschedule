package ru.studyit.studentschedule.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.util.CDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CModuleDatabase {
    @Singleton
    @Provides
    fun provideRoom(
        @ApplicationContext
        context : Context
    ) : CDatabase
    {
        return Room.databaseBuilder(
            context.applicationContext,
            CDatabase::class.java,
            "database.db"
        ).build()
    }
    @Singleton
    @Provides
    fun provideDaoLessons(
        database : CDatabase
    ) : IDAOLessons
    {
        return database.daoLessons()
    }
}