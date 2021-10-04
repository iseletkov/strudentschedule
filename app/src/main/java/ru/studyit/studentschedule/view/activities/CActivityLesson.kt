package ru.studyit.studentschedule.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import ru.studyit.studentschedule.databinding.ActivityLessonBinding

class CActivityLesson : AppCompatActivity() {
    private lateinit var binding: ActivityLessonBinding
    val formatterDate = DateTimeFormat.forPattern("YYYY-MM-DD")
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
        val dateTime = LocalDateTime.parse(dateTimeString)


        binding.editTextDate.setText(dateTime.toString(formatterDate))
        binding.editTextTime.setText(dateTime.toString(formatterTime))
    }
}