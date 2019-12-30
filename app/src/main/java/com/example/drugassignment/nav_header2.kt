package com.example.drugassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class nav_header2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_header2)

        Log.i("header", "header is called")
    }
}
