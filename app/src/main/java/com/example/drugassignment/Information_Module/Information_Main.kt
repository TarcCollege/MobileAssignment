package com.example.drugassignment.Information_Module


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentInformationMainBinding
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class Information_Main : Fragment() {

    private lateinit var binding : FragmentInformationMainBinding
    private val viewModel by viewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_information__main, container, false
        )

        val tabLayout : TabLayout = binding.infoTabLayout

        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"))
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"))
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"))

        val text = tabLayout.getTabAt(0)?.text.toString()

        setRecycleDisplay(text)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setRecycleDisplay(tab?.text.toString())

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.button.setOnClickListener {

        }

        return binding.root
    }

    fun setRecycleDisplay(text :  String) {
        val adapter = InfoAdapter()
        binding.infoMainRecycleView.adapter = adapter
        val list = arrayListOf<String>()
        for (i in 0..100)
            list.add(text)
        adapter.data = list
    }


}
