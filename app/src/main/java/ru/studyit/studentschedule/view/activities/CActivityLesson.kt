package ru.studyit.studentschedule.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import ru.studyit.studentschedule.databinding.ActivityLessonBinding
import java.util.*

class CActivityLesson : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    //val formatterDate = DateTimeFormat.forPattern("yyy-MM-DD")
    val formatterTime = DateTimeFormat.forPattern("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val index = intent.getIntExtra("PARAM_LESSON_INDEX", -1)

        var subject = intent.getStringExtra("PARAM_LESSON_SUBJECT")
        var dateTimeString = intent.getStringExtra("PARAM_LESSON_DATE")

        binding.editTextSubject.setText(subject)

        var dateTime = LocalDateTime.parse(dateTimeString)

        var date : LocalDate? = null
        var time : LocalTime? = null

        binding.calendarView.date = dateTime.toDate().time

        if (Build.VERSION.SDK_INT >= 23 )
        {
            binding.timePicker.hour = dateTime.hourOfDay
            binding.timePicker.minute = dateTime.minuteOfHour
        }
        else
        {
            binding.timePicker.currentHour = dateTime.hourOfDay
            binding.timePicker.currentMinute = dateTime.minuteOfHour
        }

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

            subject = binding.editTextSubject.text.toString()
            //val  = LocalDate.fromDateFields(Date(binding.calendarView.date))

            date?.let {
                dateTime = dateTime
                    .withYear(it.year)
                    .withMonthOfYear(it.monthOfYear)
                    .withDayOfMonth(it.dayOfMonth)
            }

            time?.let {
                dateTime = dateTime
                    .withHourOfDay(it.hourOfDay)
                    .withMinuteOfHour(it.minuteOfHour)
            }


            //Завершить активность с передачей данных в родительскую активность.
            val intent = Intent()
            intent.putExtra("PARAM_ACTIVITY_NAME", "CActivityLesson")
            intent.putExtra("PARAM_LESSON_INDEX", index)
            intent.putExtra("PARAM_LESSON_SUBJECT", subject)
            intent.putExtra("PARAM_LESSON_DATE", dateTime.toString())

            setResult(RESULT_OK, intent)
            finish()

        }
    }
}