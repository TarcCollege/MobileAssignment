package com.example.drugassignment.Donation_Module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.drugassignment.R

/**
 * A simple [Fragment] subclass.
 */
class Donation_Venue_Information : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donation__venue__information, container, false)
    }


}