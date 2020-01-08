package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentNewMember2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * A simple [Fragment] subclass.
 */
class NewMember2 : Fragment() {

    private lateinit var binding : FragmentNewMember2Binding
    private lateinit var dataListener : ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_new_member2, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val email = activity!!.intent.getStringExtra(getString(R.string.passEmail))
//        val role = activity?.intent?.getStringExtra(getString(R.string.passRole))
        var role = activity?.intent?.getStringExtra(getString(R.string.passRole))



        role = if (role == "Mentee") {
            "Mentor"
        } else {
            "Mentee"
        }
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        val docRef = mFirestore.collection("User")
            .whereEqualTo("role", role)
            .whereEqualTo("availability", true)

        dataListener = docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(CurrentUser::class.java)
                    val adapter2 = MentorMenteeAdapter(this.activity!!)
                    binding.memberListRecycleView.adapter = adapter2
                    adapter2.data = adapter
                }
            }

    }

    override fun onPause() {
        super.onPause()
        dataListener.remove()
    }
}
