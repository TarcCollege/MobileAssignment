package com.example.drugassignment.Donation_Module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentDonationVenueInformationBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class Donation_Venue_Information : Fragment() {

    private lateinit var binding: FragmentDonationVenueInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_donation__venue__information, container, false
        )

        val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.buttonSubmit.setOnClickListener {
            if (validation()) {
                navController.navigate(R.id.action_donation_Venue_Information_to_donation_Thankyou)
            }
        }

        return binding.root
    }

    private fun validation() :Boolean {
        if (binding.editAddress.text.isNullOrBlank() || binding.editDisplayName.text.isNullOrBlank()
            ||binding.editRegisterEmail.text.isNullOrBlank() ||binding.editTextSize.text.isNullOrBlank() || binding.editTextPhone.text.isNullOrBlank()) {
            if (binding.editAddress.text.isNullOrBlank()) {
                binding.editAddressLayout.error = "No Blank"
            } else {
                binding.editAddressLayout.isErrorEnabled = false
            }

            if (binding.editDisplayName.text.isNullOrBlank()) {
                binding.editDisplayNameLayout.error = "No Blank"
            } else {
                binding.editDisplayNameLayout.isErrorEnabled = false
            }
            if (binding.editTextSize.text.isNullOrBlank()) {
                binding.editSizeLayout.error = "No Blank"
            } else {
                binding.editSizeLayout.isErrorEnabled = false
            }
            if (binding.editTextPhone.text.isNullOrBlank()) {
                binding.editPhoneLayout.error = "No Blank"
            } else {
                binding.editPhoneLayout.isErrorEnabled = false
            }

            if (binding.editRegisterEmail.text.isNullOrBlank()) {
                binding.editRegisterEmailLayout.error = "No Blank"
            } else{
                binding.editRegisterEmailLayout.isErrorEnabled = false
            }



            return false
        }
        return true
    }


}
