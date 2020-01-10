package com.example.drugassignment.Donation_Module


import android.content.Context
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
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_donation__fund.*
import kotlinx.android.synthetic.main.fragment_registration.*


/**
 * A simple [Fragment] subclass.
 */
class Donation_Fund : Fragment() {
    val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
    val email = sharedPreferences?.getString(context?.getString(R.string.passEmail),"123")
    val name = sharedPreferences?.getString(context?.getString(R.string.passDisplayName),"123")
    val date = sharedPreferences?.getString(context?.getString(R.string.passDate),"123")
    val address = sharedPreferences?.getString(context?.getString(R.string.passAddress),"123")
    val role = sharedPreferences?.getString(context?.getString(com.example.drugassignment.R.string.passRole), "123")
    val availability = sharedPreferences?.getBoolean(context?.getString(com.example.drugassignment.R.string.passAvailable), false)
    var amount:Double = 0.0

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
            //to show the input field once other amount is selected
            textViewSpecifyAmount?.visibility = View.VISIBLE
            editAmount?.visibility = View.VISIBLE

        }

        val editDisplayFirstName = view?.findViewById<TextInputEditText>(R.id.editDisplayFirstName)
        val editRegisterEmail = view?.findViewById<TextInputEditText>(R.id.editRegisterEmail)
        val editTextPhone = view?.findViewById<TextInputEditText>(R.id.editTextPhone)
        if(!availability){
        editDisplayFirstName?.text = name
            editRegisterEmail?.text = email
        }

        val btn100 = view?.findViewById<Button>(R.id.btn100)
        val btn200 = view?.findViewById<Button>(R.id.btn200)

        btn100?.setOnClickListener {
            amount = 100.00
            editAmount?.visibility = View.VISIBLE
            editAmount?.text = 100.00.toString()
        }

        btn200?.setOnClickListener {
            amount = 200.00
            editAmount?.visibility = View.VISIBLE
            editAmount?.text = 200.00.toString()
        }





        val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.buttonNext.setOnClickListener {
            navController.navigate(R.id.action_donation_Fund_to_donation_Fund_Payment)
        }

        return binding.root
    }




}
