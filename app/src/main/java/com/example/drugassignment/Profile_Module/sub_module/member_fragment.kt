package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.NewMember2
import com.example.drugassignment.Profile_Module.Adapter
import com.example.drugassignment.Profile_Module.ProfileViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentMemberFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_member_fragment.*
import java.lang.reflect.Member

/**
 * A simple [Fragment] subclass.
 */
class member_fragment : Fragment(){

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var binding : FragmentMemberFragmentBinding
    private lateinit var loginViewModel: LoginViewModel
    private val memberViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_member_fragment, container, false)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setUpButton()
        observeAuthenticationState()
        initFirestore()
        initRecyclerView()



        return binding.root
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        // Get the 50 highest rated restaurants
        mQuery = mFirestore
            .collection("User")

    }

    private fun initRecyclerView() {
            var user = FirebaseAuth.getInstance().currentUser
            var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
            //var user2 : CurrentUser
            val email : String = user?.email?:""
            val docRef = mFirestore.collection("User")
                .document(email)
                .collection("SubUser")

            Log.i("user", docRef.get().isSuccessful.toString())

            docRef
                .get().addOnSuccessListener { documentSnapshot ->
                    val adapter = documentSnapshot.toObjects(SubUser::class.java)
                    val adapter2 = MemberAdapter()

                    binding.memberRecycleView.adapter = adapter2

                    adapter2.data = adapter
                }


    }

    private fun setUpButton() {
        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
        fab.isVisible = false
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)

        Log.i("avaiable", loginViewModel.currentUser.value!!.availability.toString())

    }

    private fun observeAuthenticationState() {
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
            loginViewModel.currentUser.observe(this, Observer {
                if (it.role == "Mentee") {
                    if(it.availability) {
                        btn.isVisible = true
                        btn.text = "Find Mentor"
//                        btn.setOnClickListener {
//                            function()
//                        }
                    }else {
                        btn.isVisible = false
                    }
                } else {

                        btn.isVisible = true
                        btn.text = "Find Mentee"

                }
            })

    }

}
