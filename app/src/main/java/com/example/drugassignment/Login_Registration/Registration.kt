package com.example.drugassignment.Login_Registration


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Registration : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registration, container, false
        )

//        val fab: FloatingActionButton? = activity?.findViewById(R.id.fab2)
//        fab?.isVisible = false
//        setHasOptionsMenu(false)

        //observeAuthenticationState()
        // initilize firebase instance
        auth = FirebaseAuth.getInstance()

        binding.btnRegistration.setOnClickListener {
            registrationFlow()
        }

        binding.linear2.setOnClickListener{
            hideKeyboard()
        }

        return binding.root
    }

    private fun registrationFlow() {
        hideKeyboard()
        regiser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun regiser() {
        if(!validation()) {
            return
        }
        val email = binding.editRegisterEmail.text.toString()
        val password = binding.editPassword.text.toString()
        val name = binding.editDisplayName.text.toString()

        if (email.isNullOrEmpty()) {
            binding.editRegisterEmailLayout.error = "Cannot Be Blacnk"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Game", "createUserWithEmail:success")
                    Toast.makeText(
                        activity, "Successful create an account",
                        Toast.LENGTH_SHORT
                    ).show()

                    addToDatabase()

                    var user = auth.currentUser
//
//
                    val profileUpdates: UserProfileChangeRequest =
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build()


                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Game", "updateDIsplayNameSuccecss")
                            } else {
                                Log.d("Game", "Fail")
                            }
//                            findNavController().navigate(R.id.action_registration_to_homeFragment)
                        }

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    activity, "Sent verification Email, Please Verify " +
                                            "Your Email Before Login",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_registration_to_homeFragment)
                            } else {
                                Toast.makeText(
                                    activity, "Not verification Email" + task.exception,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Game", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "Wrong Email or Email Being Registered",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }

    private fun validation() : Boolean{
        if (binding.editRegisterEmail.text.isNullOrBlank()) {
            binding.editRegisterEmailLayout.error = "No Empty"
        } else if (binding.editDisplayName.text.isNullOrBlank()) {
            binding.editDisplayNameLayout.error = "No Empty"
        }else if (binding.editPassword.text.isNullOrBlank()) {
            binding.editPasswordLayout.error = "No Empty"
        } else if (binding.editAddress.text.isNullOrBlank()) {
            binding.editAddressLayout.error = "No Empty"
        }
        return true
    }
    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken,0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addToDatabase() {
        val mFirestore = FirebaseFirestore.getInstance();
        val user = mFirestore.collection("User")

        val email = binding.editRegisterEmail.text.toString()
        val display = binding.editDisplayName.text.toString()
        val address = binding.editAddress.text.toString()
        val role = getRole()
        val date = "December"
        val availability = true

        user.document(email).set(CurrentUser(display,email,address,role,date,availability))
    }

    private fun getRole() : String{
        return if (binding.btnMentee.isChecked) {
            "Mentee"
        } else {
            "Mentor"
        }
    }


}
