package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentReminderFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class reminder_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentReminderFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reminder_fragment, container, false)

//        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
//        fab.isVisible = false

        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        btn.isVisible = false

        return inflater.inflate(R.layout.fragment_reminder_fragment, container, false)
    }


}
