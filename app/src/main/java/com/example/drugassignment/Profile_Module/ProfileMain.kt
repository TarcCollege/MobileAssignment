package com.example.drugassignment.Profile_Module


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentProfileMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 */
class ProfileMain : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private val memberViewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: FragmentProfileMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //observeAuthenticationState()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile_main, container, false
        )

        //val viewPager2 : ViewPager2 = activity!!.findViewById(R.id.viewPager)
        binding.viewPager.adapter = ViewPagerAdapter(activity!!)

        val tabLayout : TabLayout = activity!!.findViewById(R.id.tabLayout)

        tabLayout.setupWithViewPager(binding.viewPager, listOf("Progression", "Reminder", "Notification", "Mentee"))

        observeAuthenticationState()

        return binding.root
    }

    fun TabLayout.setupWithViewPager(viewPager: ViewPager2, labels: List<String>) {

        if (labels.size != viewPager.adapter?.itemCount)
            throw Exception("The size of list and the tab count should be equal!")

        TabLayoutMediator(this, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = labels[position]
            }).attach()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    viewModel.setCurrentUser()
                }
            }
        })
    }

}
