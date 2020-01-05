package com.example.drugassignment.Login_Registration


import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.drugassignment.Profile_Module.ProfileViewModel
import com.example.drugassignment.databinding.FragmentLoginBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_login.view.*


/**
 * A simple [Fragment] subclass.
 */
class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth


    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: ProfileViewModel
    private val viewModel2 by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.drugassignment.R.layout.fragment_login, container, false
        )

//        val fab: FloatingActionButton? = activity?.findViewById(com.example.drugassignment.R.id.fab2)
//        fab?.isVisible = false


        auth = FirebaseAuth.getInstance()


        binding.btnReset.paintFlags = binding.btnReset.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.btnRegistration.setOnClickListener {
            it.findNavController().navigate(com.example.drugassignment.R.id.action_login_to_registration)
        }

        binding.btnLogin.setOnClickListener {
            loginFlow()
        }

//        binding.btnReset.setOnClickListener {
//            it.findNavController().navigate(com.example.drugassignment.R.id.action_login_to_resetPassword)
//        }
        setHasOptionsMenu(true)

        binding.btnReset.setOnClickListener {
            it.findNavController().navigate(com.example.drugassignment.R.id.action_login_to_resetPassword)
        }


        binding.linear1.setOnClickListener{
            hideKeyboard()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        } ?: throw Throwable("invalid activity")
        viewModel.updateActionBarTitle("Custom Title From Fragment")
    }

    private fun loginFlow() {
        login()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    private fun login() {
        hideKeyboard()
        if (!validation()) {
            return
        }

        val email = binding.editRegisterEmail.text.toString()
        val password = binding.editPassword.text.toString()

        binding.btnLogin.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Game", "signInWithEmail:success")

                    val user = FirebaseAuth.getInstance().currentUser

                    //findNavController().navigate(com.example.drugassignment.R.id.action_login_to_homeFragment)

                    user?.let {
                        if (!user.isEmailVerified) {
                            FirebaseAuth.getInstance().signOut()
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(
                                activity, "Please Verify Your Email First",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                activity, "Welcome " + user.displayName,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel2.login = true
                            findNavController().navigate(com.example.drugassignment.R.id.action_login_to_profile_Activity)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    binding.btnLogin.isEnabled = true
                    Log.w("Game", "signInWithEmail:failure", task.exception)

                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validation() : Boolean {
        if (binding.editRegisterEmail.text.isNullOrBlank() || binding.editPassword.text.isNullOrBlank()) {
            if(binding.editRegisterEmail.text.isNullOrBlank()) {
                binding.editEmailLayout.error = "No Empty"
            } else {
                binding.editEmailLayout.isErrorEnabled = false
            }

            if (binding.editPassword.text.isNullOrBlank()) {
                binding.editPasswordLayout.error = "No Empty"
            } else {
                binding.editPasswordLayout.isErrorEnabled = false
            }
            return false
        }

        return true
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken,0)
    }


}
