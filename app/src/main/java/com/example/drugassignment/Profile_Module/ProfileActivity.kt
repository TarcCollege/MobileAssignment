package com.example.drugassignment.Profile_Module

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.Profile_Module.sub_module.MemberList
import com.example.drugassignment.Profile_Module.sub_module.ProfileViewModel2
import com.example.drugassignment.R
import com.example.drugassignment.databinding.ActivityProfileBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Profile_Activity : AppCompatActivity() {


    private lateinit var viewModel: ProfileViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel2
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel2::class.java)

        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })

        //Initialise the Shared Preferences
        sharedPreferences = getSharedPreferences("PREF_NAME",Context.MODE_PRIVATE)
        Log.i("Share1",  sharedPreferences.getString(getString(R.string.passEmail), "123"))

        setUpUI()
       // observeAuthenticationState()

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        val viewPager2: ViewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = ViewPagerAdapter(this)

        val tabLayout: TabLayout = this.findViewById(R.id.tabLayout)

        tabLayout.setupWithViewPager(
            viewPager,
            listOf("Progression", "Reminder", "Notification", "Mentee")
        )

        binding.buttonOtherUser.setOnClickListener {
            startOtherUser()
        }



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
            super.onBackPressed()
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

//    private fun observeAuthenticationState() {
//        loginViewModel.authenticationState?.observe(this, Observer { authenticationState ->
//            when (authenticationState) {
//                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
//                    loginViewModel.setCurrentUser()
//                }
//            }
//        })
//        loginViewModel.currentUser.observe(this, Observer {
//            binding.titleText.title = it.displayName
//            binding.progressionText.text = it.email
//
//            val fab: FloatingActionButton = findViewById(R.id.fab2)
//
//            val tabLayout: TabLayout = this.findViewById(R.id.tabLayout)
//            if (it.role == "Mentee") {
//                tabLayout.getTabAt(3)?.text = "Mentor"
//            } else {
//                tabLayout.getTabAt(3)?.text = "Mentee"
//            }
//
//            fab.isVisible = loginViewModel.currentUser.value!!.availability
//
//            fab.setOnClickListener {
//                //addSubMember()
//            }
//
//        })
//
////        binding.titleText.title = loginViewModel.currentUser.value?.displayName
////            binding.progressionText.text = loginViewModel.currentUser.value?.email
//    }

    fun TabLayout.setupWithViewPager(viewPager: ViewPager2, labels: List<String>) {

        if (labels.size != viewPager.adapter?.itemCount)
            throw Exception("The size of list and the tab count should be equal!")

        TabLayoutMediator(this, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()
    }

    fun startOtherUser() {
//        val intent = Intent(this, MemberList::class.java).apply {
//            putExtra(getString(R.string.passEmail), )
//        }
//        startActivity(intent)
    }

    fun setUpUI() {
        val name = sharedPreferences.getString(getString(R.string.passDisplayName), "123")
        val email = sharedPreferences.getString(getString(R.string.passEmail), "123")
        val role =  sharedPreferences.getString(getString(R.string.passRole), "123")
        val address = sharedPreferences.getString(getString(R.string.passAddress), "123")
        val avaiable = sharedPreferences.getBoolean(getString(R.string.passAvailable), false)

        //profileViewModel.setData(name,email,avaiable,address,role)

        Log.i("Share",  sharedPreferences.getString(getString(R.string.passEmail), "123"))

        binding.titleText.title = name
        binding.progressionText.text = email

        val fab: FloatingActionButton = findViewById(R.id.fab2)

        val tabLayout: TabLayout = this.findViewById(R.id.tabLayout)
        if (role == "Mentee") {
            tabLayout.getTabAt(3)?.text = "Mentor"
        } else {
            tabLayout.getTabAt(3)?.text = "Mentee"
        }

        fab.isVisible = role != "Mentee"

        fab.setOnClickListener {
            //addSubMember()
        }
    }

}




