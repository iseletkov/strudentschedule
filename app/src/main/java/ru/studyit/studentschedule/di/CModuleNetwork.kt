package ru.studyit.studentschedule.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.studyit.studentschedule.util.rest.CConverterLocalDateTime
import ru.studyit.studentschedule.util.rest.CConverterUUID
import ru.studyit.studentschedule.util.rest.IServerAPITemplate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CModuleNetwork {
    private const val BASE_URL = "http://192.168.1.4:8080/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi
    {
        return Moshi.Builder()
            .add(CConverterLocalDateTime())
            .add(CConverterUUID())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideAPITemplate(
        retrofit: Retrofit
    ): IServerAPITemplate =
        retrofit.create(IServerAPITemplate::class.java)
}