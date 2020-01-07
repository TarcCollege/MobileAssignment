package com.example.drugassignment.Profile_Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.ActivityProfileBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_profile.view.*

class Profile_Activity : AppCompatActivity()  {


    private lateinit var viewModel: ProfileViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

      //  binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })

//        initFirestore()
//        initRecyclerView()

        observeAuthenticationState()

        // displaying toolbar
        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

//        val button : Button = findViewById(R.id.buttonOtherUser)
//
//        button.setOnClickListener {
//            navController.navigate(R.id.action_homeFragment_to_profile_Activity)
//        }

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
            val appbar : CollapsingToolbarLayout = findViewById(R.id.titleText)

            appbar.title = it.displayName
            appbar.progressionText.text = it.email

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
//        val mFirestore = FirebaseFirestore.getInstance()
//        // Get the 50 highest rated restaurants
//        val mQuery = mFirestore
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




