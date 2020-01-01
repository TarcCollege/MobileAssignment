package com.example.drugassignment.Profile_Module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.drugassignment.Profile_Module.sub_module.member_fragment
import com.example.drugassignment.Profile_Module.sub_module.notification_fragment
import com.example.drugassignment.Profile_Module.sub_module.progression_fragment
import com.example.drugassignment.Profile_Module.sub_module.reminder_fragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->  progression_fragment()
            1 ->  reminder_fragment()
            2 -> notification_fragment()
            else ->  member_fragment()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}
