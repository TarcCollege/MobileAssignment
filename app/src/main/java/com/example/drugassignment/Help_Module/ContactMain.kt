package com.example.drugassignment.Help_Module


import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentContactMainBinding
import kotlinx.android.synthetic.main.fragment_contact__main.*

/**
 * A simple [Fragment] subclass.
 */
class ContactMain : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentContactMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact__main, container, false
        )

            val navController = activity!!.findNavController(R.id.nav_host_fragment)

        binding.imageEmail.setOnClickListener{
            navController.navigate(R.id.action_contactMain_to_contact_Email)
        }

//        textPhone.setOnClickListener {
//            val callIntent: Intent = Uri.parse("tel:0113215708").let { number ->
//                Intent(Intent.ACTION_DIAL, number)
////                val activities: List<ResolveInfo> =  activity!!.packageManager.queryIntentActivities(
////                    intent,
////                    PackageManager.MATCH_DEFAULT_ONLY)
////                val isIntentSafe: Boolean = activities.isNotEmpty()
////
////                if (isIntentSafe) {
////                    startActivity(callIntent)
////                }
//            }
//        }

//        textWebsite.setOnClickListener {
//            val webIntent: Intent = Uri.parse("http://www.google.com").let { webpage ->
//                Intent(Intent.ACTION_VIEW, webpage)
//            }
//        }
        return binding.root
    }


}
