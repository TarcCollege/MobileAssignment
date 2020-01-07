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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
        fab.isVisible = false
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        btn.isVisible = false


        return binding.root
    }

}
