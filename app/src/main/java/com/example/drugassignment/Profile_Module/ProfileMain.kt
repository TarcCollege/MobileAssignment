package com.example.drugassignment.Profile_Module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentProfileMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile_main.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileMain : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentProfileMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //observeAuthenticationState()

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_main, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        Log.i("Profile", "Resume")
        observeAuthenticationState()

    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.btnLogout.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }

                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName?:"null"
                        val email = user.email
                        val photoUrl = user.photoUrl

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid

                        txtName.text = name
                    }
                }
                else -> {
                    Log.i("Login", "Not User")
                    findNavController().navigate(R.id.action_profileMain_to_login)
                }
            }
        })
    }


}
