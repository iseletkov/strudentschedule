package ru.studyit.studentschedule.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.studyit.studentschedule.R
import ru.studyit.studentschedule.databinding.ActivityLoginBinding

class CActivityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPref1 = applicationContext.getSharedPreferences(getString(R.string.FILE_NAME_PREFERENCES), Context.MODE_PRIVATE)
        val login1 = sharedPref1.getString(getString(R.string.PARAM_LOGIN), "")
        if (!login1.isNullOrEmpty())
        {
            val intent1 = Intent(this, CActivityLessonList::class.java)
            startActivity(intent1)

            //finish()
            return
        }

        binding.btLogin.setOnClickListener {
            val login = binding.etLogin.text.toString()

            //binding.pbLogin.visibility = View.VISIBLE
            val sharedPref = applicationContext.getSharedPreferences(getString(R.string.FILE_NAME_PREFERENCES), Context.MODE_PRIVATE)

            with (sharedPref.edit()) {
                putString( getString(R.string.PARAM_LOGIN),login)
                apply()
            }


            val intent = Intent(this, CActivityLessonList::class.java)
            startActivity(intent)
        }
    }
}