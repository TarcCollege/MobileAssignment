package com.example.drugassignment.Login_Registration


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile_main.*

/**
 * A simple [Fragment] subclass.
 */
class Login : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        binding.btnRegistration.setOnClickListener {
            it.findNavController().navigate(R.id.action_login_to_registration)
        }

        binding.btnLogin.setOnClickListener {
            loginFlow()
        }

        binding.btnReset.setOnClickListener {
            it.findNavController().navigate(R.id.action_login_to_resetPassword)
        }

        return binding.root
    }

    private fun loginFlow() {
        login()
    }

    private fun login() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Game", "signInWithEmail:success")

                    val user = FirebaseAuth.getInstance().currentUser

                    user?.let {
                        if(!user.isEmailVerified) {
                            FirebaseAuth.getInstance().signOut()
                            Toast.makeText(activity, " Not Verified",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Verified",
                                Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.profileMain)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Game", "signInWithEmail:failure", task.exception)

                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }


            }
    }


}
