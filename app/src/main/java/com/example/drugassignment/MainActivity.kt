package com.example.drugassignment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_nav_header2.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.contactMain,
                R.id.donation_Main,
                R.id.information_Main,
                R.id.mainActivity,
                R.id.quiz_Main
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //addHeaderView()
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


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.login -> {
            if (!viewModel.login) {
                //item.onNavDestinationSelected(navController)
                item.onNavDestinationSelected(navController)
                true

            } else {
                navigateProfile()
                true
            }
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            Log.i("somethigasd", "fail")
            super.onOptionsItemSelected(item)
        }
    }

    // when the authentication change, the Nav View Header will change according
    // to what the state of the user
    private fun observeAuthenticationState() {
        // get the Nav view from the layout
        val navView: NavigationView = findViewById(R.id.nav_view)

        val headerView = navView.getHeaderView(0)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var user = FirebaseAuth.getInstance().currentUser

        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            if (authenticationState == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                viewModel.setCurrentUser()
            }
        })

        viewModel.currentUser.observe(this, Observer {
            if (it != null) {
                headerView.textViewDisplayName.text = it.displayName
                headerView.textViewHeaderEmail.text = it.email
                if (viewModel.login) {
                    headerView.setOnClickListener {
                        // closing with animation
                        // rawerLayout.closeDrawers()
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        navigateProfile()
                    }
                } else {
                    headerView.setOnClickListener {
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        navController.navigate(R.id.action_homeFragment_to_login)
                    }
                }
            } else {
                headerView.textViewDisplayName.text = "Name"
                headerView.textViewHeaderEmail.text = "Email"
                if (viewModel.login) {
                    headerView.setOnClickListener {
                        // closing with animation
                        // rawerLayout.closeDrawers()
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        navigateProfile()
                    }
                } else {
                    headerView.setOnClickListener {
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        navController.navigate(R.id.action_homeFragment_to_login)
                    }
                }
            }
        })
    }


    fun navigateProfile() {
        val sharedPref = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)

        with(sharedPref.edit()) {
            putString(
                getString(com.example.drugassignment.R.string.passEmail),
                viewModel.currentUser.value?.email
            )
            putString(
                getString(com.example.drugassignment.R.string.passAddress),
                viewModel.currentUser.value?.address
            )
            putBoolean(
                getString(com.example.drugassignment.R.string.passAvailable),
                viewModel.currentUser.value?.availability ?: false
            )
            putString(
                getString(com.example.drugassignment.R.string.passRole),
                viewModel.currentUser.value?.role
            )
            putString(
                getString(com.example.drugassignment.R.string.passDisplayName),
                viewModel.currentUser.value?.displayName
            )
            putString(
                getString(com.example.drugassignment.R.string.passDate),
                viewModel.currentUser.value?.registerDate
            )
            apply()
        }
        Log.i("Share", sharedPref.getString(getString(R.string.passEmail), "123"))
        navController.navigate(R.id.profile_Activity)
    }

}



