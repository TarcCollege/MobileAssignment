package com.example.drugassignment.Profile_Module.sub_module


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentReminderFragmentBinding

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class reminder_fragment : Fragment() {

    private lateinit var binding : FragmentReminderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reminder_fragment, container, false)

//        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
//        fab.isVisible = false

        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        btn.isVisible = false

        loadData()

        return binding.root
    }

    fun loadData() {
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString(context?.getString(R.string.passEmail),"123")
        val date = Date()
        Log.i("emailaddres", email)

        val docRef = mFirestore.collection("User")
            .document(email!!)
            .collection("Event")
            .whereGreaterThan("startTime", date)

        val dataListener = docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.i("123456", "fail")
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(CreateDrugEvent::class.java)

                    val adapter2 = ReminderAdapter(this.activity!!)

                    Log.i("123456", adapter2.context.toString())
                    Log.i("123456", adapter.size.toString())

                    binding.recyclerViewReminder.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerViewReminder.adapter = adapter2
                    adapter2.data = adapter

                } else {
                    Log.d("123456", "Current data: null")
                }
            }
    }


}
