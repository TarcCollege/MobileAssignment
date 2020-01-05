package com.example.drugassignment

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import android.content.Intent
import android.provider.ContactsContract
import com.example.drugassignment.Information_Module.Information_MainDirections
import com.example.drugassignment.Profile_Module.Profile_Activity


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        Log.v("Game", item.itemId.toString())
//
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.login -> {

            if (!viewModel.login) {
                //item.onNavDestinationSelected(navController)
                item.onNavDestinationSelected(navController)
                true

            } else {
                Log.i("Navigat", "fail")
                navController.navigate(R.id.profile_Activity)
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
    private fun observeAuthenticationState(){
        // get the Nav view from the layout
        val navView : NavigationView = findViewById(R.id.nav_view)
        // get the header view
//        val headerView  =
//            LayoutInflater.from(this).inflate(R.layout.activity_nav_header2, null)

        val headerView = navView.getHeaderView(0)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)

        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    //navView.removeHeaderView(headerView)
                    //getdata()
//                    headerView.textViewDisplayName.text = viewModel.currentUser.displayName
//                    headerView.textViewHeaderEmail.text = viewModel.currentUser.email
//                    headerView.setOnClickListener {
//
//                        // closing with animation
//                        // rawerLayout.closeDrawers()
//
//                        drawerLayout.closeDrawer(Gravity.LEFT, false)
//                        navController.navigate(R.id.action_homeFragment_to_profile_Activity)
//                    }
                    viewModel.setCurrentUser()


                    //navView.addHeaderView(headerView)
                }
                else -> {
                   // navView.removeHeaderView(headerView)
//                    headerView.textViewDisplayName.text = "Name"
//                    headerView.textViewHeaderEmail.text = "Email"
//                    headerView.setOnClickListener {
//                        drawerLayout.closeDrawer(Gravity.LEFT, false)
//                        navController.navigate(R.id.action_homeFragment_to_login)
//                    }

                  //  navView.addHeaderView(headerView)
                }

            }
        })

        viewModel.currentUser.observe(this, Observer {
            if (it != null) {
                headerView.textViewDisplayName.text = it.displayName
                headerView.textViewHeaderEmail.text = it.email
                headerView.setOnClickListener {
                    // closing with animation
                    // rawerLayout.closeDrawers()
                    drawerLayout.closeDrawer(Gravity.LEFT, false)
                    navController.navigate(R.id.action_homeFragment_to_profile_Activity)
                }
            } else {
                headerView.textViewDisplayName.text = "Name"
                headerView.textViewHeaderEmail.text = "Email"
                headerView.setOnClickListener {
                    drawerLayout.closeDrawer(Gravity.LEFT, false)
                    navController.navigate(R.id.action_homeFragment_to_login)
                }
            }
        })
    }

    private fun addHeaderView() {
        // get the Nav view from the layout
        val navView : NavigationView = findViewById(R.id.nav_view)
        // get the header view
        val headerView  =
            LayoutInflater.from(this).inflate(R.layout.activity_nav_header2, null)
        navView.addHeaderView(headerView)
    }

//    private fun getdata() {
//        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//        //var user2 : CurrentUser
//        val email : String = viewModel.user.email?:""
//        val docRef = mFirestore.collection("DrugInfo")
//            .whereEqualTo("drugName", "Aerosol sprays")
//
//        docRef
//            .get().addOnSuccessListener { documentSnapshot ->
//                Log.i("user", viewModel.user.email)
//                val qwe = documentSnapshot.toObjects(DrugDetail::class.java) ?: DrugDetail()
//
//
//            }
//    }
}



