package com.example.drugassignment.Profile_Module


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentProfileMainBinding
import com.firebase.ui.auth.AuthUI

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
            inflater, R.layout.fragment_profile_main, container, false
        )

        binding.btnLogout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                if (it.isComplete) {
                    findNavController().navigate(R.id.action_profileMain_to_mainActivity)
                }
            }
        }
        return binding.root
    }

//    private fun observeAuthenticationState() {
//        viewModel.authenticationState?.observe(viewLifecycleOwner, Observer { authenticationState ->
//
//            // Name, email address, and profile photo Url
//            txtName.text = viewModel.user.displayName
//        })
//    }


}
