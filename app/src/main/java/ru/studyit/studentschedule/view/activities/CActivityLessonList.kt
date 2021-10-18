package ru.studyit.studentschedule.view.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import ru.studyit.studentschedule.R
import ru.studyit.studentschedule.dao.IDAOLessons
import ru.studyit.studentschedule.databinding.ActivityLessonListBinding
import ru.studyit.studentschedule.model.CLesson
import ru.studyit.studentschedule.util.CDatabase
import ru.studyit.studentschedule.view.adapters.CRecyclerViewLessonListAdapter
import java.util.UUID
import kotlin.collections.ArrayList

class CActivityLessonList : AppCompatActivity() {
    private lateinit var binding: ActivityLessonListBinding

    private val lessons = ArrayList<CLesson>()

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            when(data?.getStringExtra("PARAM_ACTIVITY_NAME"))
            {
                "CActivityLesson" -> {
                    val index = data.getIntExtra("PARAM_LESSON_INDEX", -1)
                    val subject = data.getStringExtra("PARAM_LESSON_SUBJECT")?:""
                    val sDateTime = data.getStringExtra("PARAM_LESSON_DATE")

                    val lesson = lessons[index]
                    lesson.subject = subject
                    lesson.dateTime = LocalDateTime.parse(sDateTime)

                    binding.rvLessonList.adapter?.notifyItemChanged(index)

                }
                "CActivityStudentInfo" -> {
                    val index = data?.getIntExtra("PARAM_123", 0)
                }
            }


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLessonListBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

        lessons.add(CLesson(UUID.fromString("b75d453c-96f6-48a0-9619-15dc70590d59"), "Математика", LocalDateTime.parse("2021-09-30 08:00",formatter)))
        lessons.add(CLesson(UUID.fromString("3f1a4f26-d983-4b59-a4a7-ebc59b68a34a"), "Численные методы", LocalDateTime.parse("2021-09-30 09:45",formatter)))
        lessons.add(CLesson(UUID.fromString("85fae227-4459-4897-bb53-c63bf95f6b1c"), "Физкультура", LocalDateTime.parse("2021-09-30 11:30",formatter)))


        //Обработчик клика на элемент списка, открывает форму редактирования/просмотра выбанного элемента.
        val listener = object : CRecyclerViewLessonListAdapter.IClickListener
        {
            override fun onItemClick(lesson: CLesson, index: Int) {
                val intent = Intent(this@CActivityLessonList, CActivityLesson::class.java)
                intent.putExtra("PARAM_LESSON_SUBJECT", lesson.subject)
                intent.putExtra("PARAM_LESSON_DATE", lesson.dateTime.toString())
                intent.putExtra("PARAM_LESSON_INDEX", index)
                resultLauncher.launch(intent)
            }

            override fun onItemDeleteClick(lesson: CLesson, index: Int) {
                lessons.removeAt(index);
                binding.rvLessonList.adapter?.notifyItemRemoved(index);
            }
        }


        val adapter = CRecyclerViewLessonListAdapter(lessons, listener)
        binding.rvLessonList.adapter = adapter

        binding.rvLessonList.layoutManager = LinearLayoutManager(this)

        binding.bottomNavigationLessonList.setOnItemSelectedListener { item->
            when(item.itemId) {
                R.id.miExit -> {
                    finishAffinity()
                    true
                }
                R.id.miAddLesson -> {
                    val lesson = CLesson(UUID.randomUUID(), "", LocalDateTime.now())

                    lessons.add(lesson)

                    val intent = Intent(this@CActivityLessonList, CActivityLesson::class.java)
                    intent.putExtra("PARAM_LESSON_SUBJECT", lesson.subject)
                    intent.putExtra("PARAM_LESSON_DATE", lesson.dateTime.toString())
                    intent.putExtra("PARAM_LESSON_INDEX", lessons.size-1)
                    resultLauncher.launch(intent)
                    true
                }
                else -> false
            }

        }


        val db = CDatabase.getDatabase(this)
        val daoLessons = db.daoLessons()
        lifecycleScope.launch {
            createInitialData(daoLessons)
        }


        // Create the observer which updates the UI.
        val observerLessons = Observer<List<CLesson>> { test_lessons ->
            // Update the UI
            val x=0;
        }

        daoLessons.getAll().observe(this,observerLessons)




    }
    private suspend fun createInitialData(daoLessons : IDAOLessons) = withContext(Dispatchers.IO)
    {
        lessons.forEach { lesson ->
            daoLessons.insert(lesson)


        }
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
                val sharedPref = applicationContext.getSharedPreferences(getString(R.string.FILE_NAME_PREFERENCES), Context.MODE_PRIVATE)

                with (sharedPref.edit()) {
                    putString( getString(R.string.PARAM_LOGIN),"")
                    apply()
                }

                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}