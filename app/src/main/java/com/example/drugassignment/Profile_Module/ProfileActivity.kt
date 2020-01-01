package com.example.drugassignment.Profile_Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.R
import kotlinx.android.synthetic.main.activity_profile.*

class Profile_Activity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        var list = arrayListOf<String>()
        for (i in 0..100)
            list.add("Item $i")
        val adapter = Adapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this@Profile_Activity, RecyclerView.VERTICAL,
            false)
        recyclerView.adapter = adapter


    }
}
