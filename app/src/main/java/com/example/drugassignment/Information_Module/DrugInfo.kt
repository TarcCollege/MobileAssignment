package com.example.drugassignment.Information_Module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentDrugInfoBinding
import com.google.android.gms.common.util.DataUtils

/**
 * A simple [Fragment] subclass.
 */
class DrugInfo : Fragment() {
    private lateinit var binding : FragmentDrugInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



//        val args = DrugInfoArgs.fromBundle(arguments!!)
//        Toast.makeText(context, "NumCorrect: ${args.test}, NumQuestions: ", Toast.LENGTH_LONG).show()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_drug_info, container, false
        )
        val args = DrugInfoArgs.fromBundle(arguments!!)
        binding.textView.text = args.name
        binding.txtDrugInfo.text = args.info
        binding.txtDrugSideEffect.text = args.sideEffect


        return binding.root
    }


}
