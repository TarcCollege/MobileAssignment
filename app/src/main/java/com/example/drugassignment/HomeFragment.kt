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
import kotlinx.android.synthetic.main.activity_profile.view.*
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
        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {

                    viewModel.setCurrentUser()
                    //navView.addHeaderView(headerView)
                }
                else -> {
                }

            }
        })



    }




}
