package com.example.drugassignment.Donation_Module


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentDonationFundBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_nav_header2.view.*
import kotlinx.android.synthetic.main.fragment_donation__fund.*
import kotlinx.android.synthetic.main.fragment_registration.*


/**
 * A simple [Fragment] subclass.
 */
class Donation_Fund : Fragment() {
//    val sharedPreferences = this.activity!!.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
//    val email = sharedPreferences!!.getString(activity!!.getString(R.string.passEmail),"123")
//    val name = sharedPreferences?.getString(context?.getString(R.string.passDisplayName),"123")
//    val date = sharedPreferences?.getString(context?.getString(R.string.passDate),"123")
//    val address = sharedPreferences?.getString(context?.getString(R.string.passAddress),"123")
//    val role = sharedPreferences?.getString(context?.getString(com.example.drugassignment.R.string.passRole), "123")
//    val availability = sharedPreferences?.getBoolean(context?.getString(com.example.drugassignment.R.string.passAvailable), false)
//    var amount:Double = 0.0

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding : FragmentDonationFundBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_donation__fund, container, false
        )

//        val textViewSpecifyAmount = view?.findViewById<TextView>(R.id.textViewSpecifyAmount)
//        val buttonOtherAmount = view?.findViewById<Button>(R.id.btnOtherAmount)
//        val editAmount = view?.findViewById<TextView>(R.id.editAmount)
//
//
//        btnOtherAmount.setOnClickListener{
//            //to show the input field once other amount is selected
//            textViewSpecifyAmount?.visibility = View.VISIBLE
//            editAmount?.visibility = View.VISIBLE
//
//        }

//        val editDisplayFirstName = view?.findViewById<TextInputEditText>(R.id.editDisplayFirstName)
//        val editRegisterEmail = view?.findViewById<TextInputEditText>(R.id.editRegisterEmail)
//        val editTextPhone = view?.findViewById<TextInputEditText>(R.id.editTextPhone)
//        if(!availability!!){
//        editDisplayFirstName?.setText(name)
//            editRegisterEmail?.setText(email)
//        }

//        val btn100 = view?.findViewById<Button>(R.id.btn100)
//        val btn200 = view?.findViewById<Button>(R.id.btn200)
//
//        btn100?.setOnClickListener {
//            amount = 100.00
//            editAmount?.visibility = View.VISIBLE
//            editAmount?.text = 100.00.toString()
//        }
//
//        btn200?.setOnClickListener {
//            amount = 200.00
//            editAmount?.visibility = View.VISIBLE
//            editAmount?.text = 200.00.toString()
//        }





        val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.buttonNext.setOnClickListener {
            navController.navigate(R.id.action_donation_Fund_to_donation_Fund_Payment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observedState()
    }

    fun observedState() {
        binding.textViewSpecifyAmount.isEnabled  = false
        binding.editAmount.isEnabled = false
        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            if (authenticationState == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                viewModel.setCurrentUser()
            }
        })

        viewModel.currentUser.observe(this, Observer {
            if (it != null) {
                binding.editDisplayFirstName.setText(it.displayName)
                binding.editRegisterEmail.setText(it.email)
                binding.btnOtherAmount.setOnClickListener {
                    binding.textViewSpecifyAmount.isEnabled  = true
                    binding.editAmount.isEnabled = true
                }

                binding.btn100.setOnClickListener {
                    var total= 0

                    if (binding.editAmount.text.isNullOrBlank()) {
                        total = 100
                    } else {
                        total += binding.editAmount.text.toString().toInt()
                        total += 100
                    }
                    binding.editAmount.setText(total.toString())

                }

                binding.btn200.setOnClickListener {
                    var total= 0

                    if (binding.editAmount.text.isNullOrBlank()) {
                        total = 200
                    } else {
                        total += binding.editAmount.text.toString().toInt()
                        total += 200
                    }
                    binding.editAmount.setText(total.toString())

                }


//                textViewSpecifyAmount?.visibility = View.VISIBLE
//                editAmount?.visibility = View.VISIBLE
            } else {
                Log.i("Login", "null")
            }
        })




    }




}
