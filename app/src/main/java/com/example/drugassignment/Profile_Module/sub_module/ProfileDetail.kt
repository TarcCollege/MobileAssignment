package com.example.drugassignment.Profile_Module.sub_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils

import android.view.MenuItem
import com.example.drugassignment.R
import kotlinx.android.synthetic.main.activity_profile_detial.*

class ProfileDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.drugassignment.R.layout.activity_profile_detial)


        val email =intent?.getStringExtra("Email")
        val name =  intent?.getStringExtra("DisplayName")

        txtDetailEmail.text = email
        txtDetailName.text = name

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
