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
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentNotificationFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * A simple [Fragment] subclass.
 */
class notification_fragment : Fragment() {
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var binding : FragmentNotificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notification_fragment, container, false)


        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        btn.isVisible = false


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString(context?.getString(R.string.passEmail),"123")

        Log.i("emailaddres", email)

        val docRef = mFirestore.collection("User")
            .document(email!!)
            .collection("Notification")
            .orderBy("date", Query.Direction.DESCENDING)

        val dataListener = docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(Notification::class.java)

                    val adapter2 = NotificationAdapter(this.activity!!)
                    binding.recyclerViewNotification.layoutManager = LinearLayoutManager(activity)
                    binding.recyclerViewNotification.adapter = adapter2
                    adapter2.data = adapter

                } else {
                    Log.d("123", "Current data: null")
                }
            }
    }

}
