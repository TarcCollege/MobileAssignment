package com.example.drugassignment.Profile_Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.drugassignment.R

class Profile_Activity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })
    }
}
