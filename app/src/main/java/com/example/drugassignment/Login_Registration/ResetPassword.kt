package com.example.drugassignment.Login_Registration


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentProfileMainBinding
import com.example.drugassignment.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class ResetPassword : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reset_password, container, false)

        binding.btnReset.setOnClickListener {
            resetPassword()
        }


        return binding.root
    }

    private fun resetPassword() {
        val emailAddress = binding.editText.text.toString()
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Reset", "Email sent.")
                    Toast.makeText(activity, "A Reset Password Had Sent To You Email.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Fail.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}
