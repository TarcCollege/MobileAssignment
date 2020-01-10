package com.example.drugassignment.Profile_Module.sub_module


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentProgressionFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*




/**
 * A simple [Fragment] subclass.
 */
class progression_fragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding : FragmentProgressionFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_progression_fragment, container, false)

//        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
//        fab.isVisible = false
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        btn.isVisible = false

        sharedPreferences = activity!!.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)

        val date = sharedPreferences.getString(getString(R.string.passDate),"11/1/2019")
        //val date2 = Date(date)
        val date3 = Date()

//        val diff = date3.time - date2.time
//        val seconds = diff / 1000
//        val minutes = seconds / 60
//        val hours = minutes / 60
//        val days = hours / 24

        binding.textView7.text = "You have Join For 70 days"


        return binding.root
    }


}
