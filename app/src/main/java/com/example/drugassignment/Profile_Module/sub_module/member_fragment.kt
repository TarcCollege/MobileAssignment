package com.example.drugassignment.Profile_Module.sub_module

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.Profile_Module.Profile_Activity
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentMemberFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
class member_fragment : Fragment() {

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query

    private lateinit var binding: FragmentMemberFragmentBinding
    private lateinit var profileViewModel: ProfileViewModel2
    private val viewModel by viewModels<ProfileViewModel2>()
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataListener : ListenerRegistration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.drugassignment.R.layout.fragment_member_fragment, container, false
        )

        sharedPreferences = activity!!.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel2::class.java)

        //initFirestore()
        //initRecyclerView()
        //setUpUI()

        return binding.root
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        // Get the 50 highest rated restaurants
        mQuery = mFirestore
            .collection("User")

    }

    private fun initRecyclerView() {
        val user = FirebaseAuth.getInstance().currentUser

        // get the data once
//        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//        //var user2 : CurrentUser
//        val email: String = user?.email ?: ""
//        val docRef = mFirestore.collection("User")
//            .document(email)
//            .collection("SubUser")
//
//        Log.i("user", docRef.get().isSuccessful.toString())

//        docRef
//            .get().addOnSuccessListener { documentSnapshot ->
//                Log.i("user22", user?.email)
//                val adapter = documentSnapshot.toObjects(SubUser::class.java)
//                val adapter2 = MemberAdapter()
//                binding.memberRecycleView.adapter = adapter2
//                adapter2.data = adapter
//            }

        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        //var user2 : CurrentUser
        val email: String = user?.email ?: ""
        val docRef = mFirestore.collection("User")
            .document(email)
            .collection("SubUser")

        dataListener= docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    Log.d("123", "Current data: ${snapshot.toObjects(SubUser::class.java)}")
                    val adapter = snapshot.toObjects(SubUser::class.java)
                    val adapter2 = MemberAdapter(this.activity!!)
                    binding.memberRecycleView.adapter = adapter2
                    adapter2.data = adapter
                } else {
                    Log.d("123", "Current data: null")
                }
            }
    }

//    override fun onPause() {
//        super.onPause()
//        //dataListener.remove()
//    }

    override fun onResume() {
        super.onResume()
        Log.i("Share1", "start")
        initFirestore()
        initRecyclerView()
        setUpUI()
    }

    fun setUpUI() {
        val btn: Button = activity!!.findViewById(com.example.drugassignment.R.id.buttonOtherUser)
        val available = sharedPreferences.getBoolean(context?.getString(R.string.passAvailable),true)
        val role = sharedPreferences.getString(context?.getString(R.string.passRole),"123")


        Log.i("true123",(profileViewModel.role))
        if (role == "Mentee") {
            if (available) {
                btn.isVisible = true
                btn.text = "Find Mentor"

            } else {
                btn.isVisible = false
            }
        } else {
            btn.isVisible = true
            btn.text = "Find Mentee"

        }

        btn.setOnClickListener {
            val intent = Intent(context, MemberList::class.java)
            Log.i("role,", viewModel.role)
            intent.putExtra(
                getString(com.example.drugassignment.R.string.passEmail),
                sharedPreferences.getString(getString(R.string.passEmail), "123")
            )
            intent.putExtra(
                getString(com.example.drugassignment.R.string.passDisplayName),
                sharedPreferences.getString(getString(R.string.passDisplayName), "123")
            )
            intent.putExtra(
                getString(com.example.drugassignment.R.string.passAddress),
                sharedPreferences.getString(getString(R.string.passAddress), "123")
            )
            intent.putExtra(
                getString(com.example.drugassignment.R.string.passAvailable),
                sharedPreferences.getBoolean(getString(R.string.passAvailable), false)
            )
            intent.putExtra(
                getString(com.example.drugassignment.R.string.passRole),

                sharedPreferences.getString(getString(R.string.passRole), "123")
            )
            startActivity(intent)
        }
    }

}
