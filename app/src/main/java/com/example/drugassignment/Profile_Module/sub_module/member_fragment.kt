package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Profile_Module.Adapter
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentMemberFragmentBinding
import kotlinx.android.synthetic.main.fragment_member_fragment.*
import java.lang.reflect.Member

/**
 * A simple [Fragment] subclass.
 */
class member_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentMemberFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_member_fragment, container, false)

        val adapter = MemberAdapter()
        binding.memberRecycleView.adapter = adapter

//        val nights = listOf(
//            "Something",
//            "something2",
//            "something3"
//        )

        var list = arrayListOf<String>()
        for (i in 0..100)
            list.add("Item $i")


        adapter.data = list


        return binding.root
    }


}
