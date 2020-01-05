package com.example.drugassignment.Donation_Module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentDonationFundBinding
import com.example.drugassignment.databinding.FragmentDonationMainBinding

/**
 * A simple [Fragment] subclass.
 */
class Donation_Fund : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentDonationFundBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_donation__fund, container, false
        )

        val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.buttonNext.setOnClickListener {
            navController.navigate(R.id.action_donation_Fund_to_donation_Fund_Payment)
        }

        return binding.root
    }


}
