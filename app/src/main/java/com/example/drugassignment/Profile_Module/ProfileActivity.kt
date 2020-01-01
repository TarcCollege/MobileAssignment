package com.example.drugassignment.Profile_Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.ActivityProfileBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile_Activity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })

        observeAuthenticationState()

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_action_bar_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> {
            // User chose the "Settings" item, show the app settings UI...
//            AuthUI.getInstance().signOut()
            FirebaseAuth.getInstance().signOut()
            finish()
            true
        }

        R.id.editProgile -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...

            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun observeAuthenticationState(){

        loginViewModel.authenticationState?.observe(this, Observer {
            binding.titleText.title = loginViewModel.user.displayName
            binding.progressionText.text = loginViewModel.user.email
        })
    }
}




