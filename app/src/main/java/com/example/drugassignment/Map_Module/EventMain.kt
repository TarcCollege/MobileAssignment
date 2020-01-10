package com.example.drugassignment.Map_Module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.Profile_Module.sub_module.MemberAdapter
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentEventMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.*
import java.util.Collections.list

/**
 * A simple [Fragment] subclass.
 */
class EventMain : Fragment() {
    private lateinit var binding : FragmentEventMainBinding
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var dataListener : ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_event_main, container, false
        )


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val user = FirebaseAuth.getInstance().currentUser

        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        //var user2 : CurrentUser
        val date = Date()
        val email: String = user?.email ?: ""
        val docRef = mFirestore.collection("Event")
            .whereGreaterThan("startTime", date)
            .orderBy("startTime", Query.Direction.ASCENDING)

        dataListener= docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(CreateDrugEvent::class.java)
                    val adapter2 = EventAdapter(this)
                    binding.recyclerViewEventMain.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerViewEventMain.adapter = adapter2
                    adapter2.data = adapter
                } else {
                    Log.d("123", "Current data: null")
                }
            }
    }


}
