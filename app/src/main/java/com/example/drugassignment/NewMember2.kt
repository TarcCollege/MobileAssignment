package com.example.drugassignment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.databinding.FragmentNewMember2Binding

/**
 * A simple [Fragment] subclass.
 */
class NewMember2 : Fragment() {

    private lateinit var binding : FragmentNewMember2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_new_member2, container, false)

        return binding.root
    }


}
