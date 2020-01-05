package com.example.drugassignment


import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.text.Typography.times


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController : NavController
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        navController = activity!!.findNavController(R.id.nav_host_fragment)

        observeAuthenticationState()

        enableButton()

        binding.btnInformation.setOnClickListener {
//            updateFirebase()
            navController.navigate(R.id.information_Main)
           // disableButton()
        }

        binding.btnContactUs.setOnClickListener {
            navController.navigate(R.id.contactMain)
            //disableButton()
        }

        binding.btnDonation.setOnClickListener {
            navController.navigate(R.id.donation_Main)
           // disableButton()
        }

        binding.btnQuiz.setOnClickListener {
            navController.navigate(R.id.quiz_Main)
            //disableButton()
        }

        binding.btnSupportGroup.setOnClickListener {
            navController.navigate(R.id.mapsActivity)
           disableButton()
        }

//        binding.button2.setOnClickListener {
//            updateFirebase()
//        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        enableButton()
    }

    private fun disableButton() {
        binding.btnSupportGroup.isEnabled = false
        binding.btnQuiz.isEnabled = false
        binding.btnDonation.isEnabled = false
        binding.btnContactUs.isEnabled = false
        binding.btnInformation.isEnabled = false
    }

    private fun enableButton() {
        binding.btnSupportGroup.isEnabled = true
        binding.btnQuiz.isEnabled = true
        binding.btnDonation.isEnabled = true
        binding.btnContactUs.isEnabled = true
        binding.btnInformation.isEnabled = true
    }

    private fun observeAuthenticationState(){
        // get the Nav view from the layout
        Log.i("Register", "Start")
        val navView : NavigationView = activity!!.findViewById(R.id.nav_view)
        // get the header view
//        val headerView  =
//            LayoutInflater.from(this).inflate(R.layout.activity_nav_header2, null)

        val headerView = navView.getHeaderView(0)
        val drawerLayout : DrawerLayout = activity!!.findViewById(R.id.drawer_layout)

        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    //navView.removeHeaderView(headerView)
//                    headerView.textViewDisplayName.text = viewModel.currentUser.displayName
//                    headerView.textViewHeaderEmail.text = viewModel.currentUser.email
//                    headerView.setOnClickListener {
//                        // closing with animation
//                        // rawerLayout.closeDrawers()
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

//    private fun updateFirebase() {
//        val mFirestore = FirebaseFirestore.getInstance()
//        val restaurants = mFirestore.collection("User")
//
//        for (i in 1..10) {
//            val name = randomName(10)
//            val email = randomName(10) + "@gmail.com"
//            val date = "December"
//            val address = "Kuala Lumpur"
//            val availability = true
//            val role = "Mentor"
//
//            restaurants.document(email).set(CurrentUser(name,email,address,role,date,availability))
//        }
//
//    }
//
//    private fun randomName(limit: Int): String {
//        val STRING_CHARACTERS = ('0'..'z').toList().toTypedArray()
//
//        return   (1..32).map { STRING_CHARACTERS.random() }.joinToString("")
//    }


}
