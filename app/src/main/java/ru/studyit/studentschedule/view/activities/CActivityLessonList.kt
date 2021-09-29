package ru.studyit.studentschedule.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import ru.studyit.studentschedule.R
import ru.studyit.studentschedule.databinding.ActivityLessonListBinding
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.view.adapters.CRecyclerViewLessonListAdapter

class CActivityLessonList : AppCompatActivity() {
    private lateinit var binding: ActivityLessonListBinding

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val x = data?.getIntExtra("PARAM_123", 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm")
        val lessons = ArrayList<CLesson>()
        lessons.add(CLesson("Математика", LocalDateTime.parse("2021-09-30 08:00",formatter)))
        lessons.add(CLesson("Численные методы", LocalDateTime.parse("2021-09-30 09:45",formatter)))
        lessons.add(CLesson("Физкультура", LocalDateTime.parse("2021-09-30 11:30",formatter)))

        val adapter = CRecyclerViewLessonListAdapter(lessons)
        binding.rvLessonList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_lesson_list_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.miAccountDetails -> {
                val intent = Intent(this, CActivityStudentInfo::class.java)
                resultLauncher.launch(intent)
                true
            }
            R.id.miExit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}