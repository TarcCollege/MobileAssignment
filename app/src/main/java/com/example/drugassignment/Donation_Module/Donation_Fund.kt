package com.example.drugassignment.Donation_Module


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentDonationFundBinding
import kotlinx.android.synthetic.main.fragment_donation__fund.*


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
        val textViewSpecifyAmount = view?.findViewById<TextView>(R.id.textViewSpecifyAmount)
        val buttonOtherAmount = view?.findViewById<Button>(R.id.btnOtherAmount)
        val editAmount = view?.findViewById<TextView>(R.id.editAmount)
        btnOtherAmount.setOnClickListener{
            textViewSpecifyAmount?.visibility = View.VISIBLE
            editAmount?.visibility = View.VISIBLE
        }


        val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.buttonNext.setOnClickListener {
            navController.navigate(R.id.action_donation_Fund_to_donation_Fund_Payment)
        }

        return binding.root
    }




}
