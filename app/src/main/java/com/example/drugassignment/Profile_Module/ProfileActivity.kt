package com.example.drugassignment.Profile_Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.drugassignment.Information_Module.DrugDetailAdapter
import com.example.drugassignment.Information_Module.Information_Main
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.Profile_Module.sub_module.MemberAdapter2
import com.example.drugassignment.R
import com.example.drugassignment.databinding.ActivityProfileBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile_Activity : AppCompatActivity()  {


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

//        initFirestore()
//        initRecyclerView()

        observeAuthenticationState()

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)



        val viewPager2 : ViewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = ViewPagerAdapter(this)

        val tabLayout : TabLayout = this.findViewById(R.id.tabLayout)

        tabLayout.setupWithViewPager(viewPager, listOf("Progression", "Reminder", "Notification", "Mentee"))

//        val message = intent.getStringExtra("Value")
//        binding.progressionText.text = message

//        var list = arrayListOf<String>()
//        for (i in 0..100)
//            list.add("Item $i")
//        val adapter = Adapter(list)
//        recyclerView.layoutManager = LinearLayoutManager(this@Profile_Activity, RecyclerView.VERTICAL,
//            false)
//        recyclerView.adapter = adapter


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

    private fun observeAuthenticationState(){
        loginViewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    loginViewModel.setCurrentUser()
                }
            }
        })
        loginViewModel.currentUser.observe(this, Observer {
            binding.titleText.title = it.displayName
            binding.progressionText.text = it.email

            val fab: FloatingActionButton = findViewById(R.id.fab2)

            val tabLayout : TabLayout = this.findViewById(R.id.tabLayout)
            if (it.role == "Mentee"){
                tabLayout.getTabAt(3)?.text = "Mentor"
            } else {
                tabLayout.getTabAt(3)?.text = "Mentee"
            }

            fab.isVisible = loginViewModel.currentUser.value!!.availability

            fab.setOnClickListener {
                addSubMember()
            }

        })

//        binding.titleText.title = loginViewModel.currentUser.value?.displayName
//            binding.progressionText.text = loginViewModel.currentUser.value?.email
    }

    fun TabLayout.setupWithViewPager(viewPager: ViewPager2, labels: List<String>) {

        if (labels.size != viewPager.adapter?.itemCount)
            throw Exception("The size of list and the tab count should be equal!")

        TabLayoutMediator(this, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()
    }

//    private fun initFirestore() {
//        mFirestore = FirebaseFirestore.getInstance()
//        // Get the 50 highest rated restaurants
//        mQuery = mFirestore
//            .collection("DrugInfo")
//            .orderBy("drugType", Query.Direction.DESCENDING)
//        Log.i("123",mQuery.toString())
//    }
//
//    private fun initRecyclerView() {
//        mAdapter = object : MemberAdapter2(mQuery, this@Profile_Activity) {
//            override fun onError(e: FirebaseFirestoreException?) { // Show a snackbar on errors
////                Snackbar.make(view!!.findViewById(android.R.id.content),
////                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
//            }
//        }
//        binding.infoMainRecycleView.layoutManager = LinearLayoutManager(activity)
//        binding.infoMainRecycleView.adapter = mAdapter
//    }

    fun addSubMember() {

    }
}




