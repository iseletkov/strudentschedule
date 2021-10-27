package ru.studyit.studentschedule.util.rest

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CRetrofitBuilder {
    companion object {
        private const val BASE_URL = "http://192.168.1.4:8080/"

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getRetrofit(): Retrofit {
            val tempInstance = CRetrofitBuilder.INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val moshi = Moshi.Builder()
                    .add(CConverterLocalDateTime())
                    .add(CConverterUUID())
                    .build()


                val instance = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()


                    CRetrofitBuilder.INSTANCE = instance
                return instance
            }
        }
    }
}