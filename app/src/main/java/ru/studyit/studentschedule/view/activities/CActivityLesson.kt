package ru.studyit.studentschedule.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import ru.studyit.studentschedule.R
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.databinding.ActivityLessonBinding
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.util.CDatabase
import ru.studyit.studentschedule.util.rest.CRetrofitBuilder
import ru.studyit.studentschedule.util.rest.IServerAPITemplate
import java.util.*

class CActivityLesson : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    //val formatterDate = DateTimeFormat.forPattern("yyy-MM-DD")
    private val formatterTime = DateTimeFormat.forPattern("HH:mm")

    private var date : LocalDate? = null
    private var time : LocalTime? = null

    private lateinit var lesson : CLesson
    private lateinit var daoLessons : IDAOLessons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = CDatabase.getDatabase(this)
        daoLessons = db.daoLessons()
        val sId = intent.getStringExtra(getString(R.string.PARAM_LESSON_ID))
        val id = UUID.fromString(sId)
        lifecycleScope.launch {
            lesson = daoLessons.findById(id)?: CLesson(id, "", LocalDateTime.now())
                showLesson()
        }



        /*val index = intent.getIntExtra("PARAM_LESSON_INDEX", -1)

        var subject = intent.getStringExtra("PARAM_LESSON_SUBJECT")
        var dateTimeString = intent.getStringExtra("PARAM_LESSON_DATE")
        */



        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            date = LocalDate.now()
                .withYear(year)
                .withMonthOfYear(month+1)
                .withDayOfMonth(dayOfMonth)

        }
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minuteOfHour ->

            time = LocalTime.now()
                .withHourOfDay(hourOfDay)
                .withMinuteOfHour(minuteOfHour)
        }
        




        binding.buttonSave.setOnClickListener {
            //Сохранить данные

            lesson.subject = binding.editTextSubject.text.toString()
            //val  = LocalDate.fromDateFields(Date(binding.calendarView.date))



            date?.let {
                lesson.dateTime = lesson.dateTime
                    .withYear(it.year)
                    .withMonthOfYear(it.monthOfYear)
                    .withDayOfMonth(it.dayOfMonth)
            }

            time?.let {
                lesson.dateTime = lesson.dateTime
                    .withHourOfDay(it.hourOfDay)
                    .withMinuteOfHour(it.minuteOfHour)
            }

            lifecycleScope.launch {

                val lessonFromDB = daoLessons.findById(id)
                lessonFromDB?.let {
                    daoLessons.update(lesson)
                } ?: run {
                    daoLessons.insert(lesson)
                }

                val retrofit = CRetrofitBuilder.getRetrofit()
                val service = retrofit.create(IServerAPITemplate::class.java)
                service.saveLesson(lesson)

            }
            finish()

        }

        binding.buttonMap.setOnClickListener {
            val intent = Intent(this, CActivityMap::class.java)
            startActivity(intent)
        }


    }
    /*
     * Отображение данных из урока на форме.
     */
    private fun showLesson()
    {
        binding.editTextSubject.setText(lesson.subject)

        binding.calendarView.date = lesson.dateTime.toDate().time

        if (Build.VERSION.SDK_INT >= 23 )
        {
            binding.timePicker.hour = lesson.dateTime.hourOfDay
            binding.timePicker.minute = lesson.dateTime.minuteOfHour
        }
        else
        {
            binding.timePicker.currentHour = lesson.dateTime.hourOfDay
            binding.timePicker.currentMinute = lesson.dateTime.minuteOfHour
        }
    }
}