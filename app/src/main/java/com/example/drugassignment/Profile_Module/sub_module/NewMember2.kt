package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentNewMember2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val email = activity!!.intent.getStringExtra(getString(R.string.passEmail))
//        val role = activity?.intent?.getStringExtra(getString(R.string.passRole))
        val role = "Mentee"

//        Log.i("rile", role)

        val adapter2 = MentorMenteeAdapter()
        binding.memberListRecycleView.adapter = adapter2

        val user = FirebaseAuth.getInstance().currentUser
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        //var user2 : CurrentUser

        val docRef = mFirestore.collection("User")
            .whereEqualTo("role", role)

        Log.i("user", docRef.get().isSuccessful.toString())

        docRef
            .get().addOnSuccessListener { documentSnapshot ->
                Log.i("user22", user?.email)
                val adapter = documentSnapshot.toObjects(SubUser::class.java)
                adapter2.data = adapter
            }

//        val email = "12"
//        val role = "123"
//        val name = "123"
//        val qwe = "123"
//
//        val adapter2 = MentorMenteeAdapter()
//        binding.memberListRecycleView.adapter = adapter2
//
//
////        val nights = listOf(
////            "Something",
////            "something2",
////            "something3"
////        )
//
//        val list = arrayListOf<SubUser>()
//        for (i in 0..100)
//            list.add(SubUser("Kuek", "kuekyb@gmail","Klang","30 days"))
//        adapter2.data = list



    }


}
