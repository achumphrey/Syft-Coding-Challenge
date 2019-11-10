package com.example.syftreposearchapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.ui.adapter.LanguageAdapter
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        rv_languages.layoutManager = LinearLayoutManager(this)
        val adapter =
            LanguageAdapter(
                listOf("Java", "Kotlin", "JavaScript", "C#", "C++", "Ruby","Python"),
                intent?.getStringExtra("selected_language") ?: ""
            )
        rv_languages.adapter = adapter

        btnApply.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selected_language", adapter.selectedLanguage)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}