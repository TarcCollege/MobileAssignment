package com.example.drugassignment.Login_Registration


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentRegistrationBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

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

        val fab: FloatingActionButton? = activity?.findViewById(R.id.fab2)
        fab?.isVisible = false
        setHasOptionsMenu(false)

        //observeAuthenticationState()
        // initilize firebase instance
        auth = FirebaseAuth.getInstance()

        binding.btnRegistration.setOnClickListener {
            registrationFlow()
        }

        return binding.root
    }

    private fun registrationFlow() {
        regiser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun regiser() {
        val email = binding.editRegisterEmail.text.toString()
        val password = binding.editPassword.text.toString()
        val name = binding.editName.text.toString()

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

                    var user = auth.currentUser

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
                                    activity, "Sent verification Email",
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
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }

}
