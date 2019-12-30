package com.example.drugassignment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//
//        fab.isVisible = false
//
//        fab.setOnClickListener { view ->
//
//        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.action_homeFragment_to_contactMain,
                R.id.action_homeFragment_to_donation_Main,
                R.id.action_homeFragment_to_information_Main,
                R.id.action_homeFragment_to_mapsActivity,
                R.id.action_homeFragment_to_profile_Activity,
                R.id.action_homeFragment_to_quiz_Main
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        observeAuthenticationState()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_action_bar_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        Log.v("Game", item.itemId.toString())

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    // when the authentication change, the Nav View Header will change according
    // to what the state of the user
    private fun observeAuthenticationState(){
        // get the Nav view from the layout
        val navView : NavigationView = findViewById(R.id.nav_view)
        // get the header view
        val headerView  =
            LayoutInflater.from(this).inflate(R.layout.activity_nav_header2, null)

        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    headerView.textViewDisplayName.text = viewModel.user.displayName
                    headerView.textViewHeaderEmail.text = viewModel.user.email
                    navView.addHeaderView(headerView)
                }
                else ->
                    navView.addHeaderView(headerView)
            }
        })
    }
}



