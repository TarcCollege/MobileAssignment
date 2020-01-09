package com.example.drugassignment.Profile_Module.sub_module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils

import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.R
import com.example.drugassignment.databinding.ActivityProfileDetialBinding
import kotlinx.android.synthetic.main.activity_profile_detial.*

class ProfileDetail : AppCompatActivity() {

    private lateinit var binding : ActivityProfileDetialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.drugassignment.R.layout.activity_profile_detial)

        binding  =  DataBindingUtil.setContentView(this, R.layout.activity_profile_detial)

        setupUI()

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

    private fun setupUI() {
        val email =intent?.getStringExtra("Email")
        val name =  intent?.getStringExtra("DisplayName")
        txtDetailEmail.text = email
        txtDetailName.text = name
    }
}
