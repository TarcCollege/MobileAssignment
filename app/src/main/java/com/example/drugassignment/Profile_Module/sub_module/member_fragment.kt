package com.example.drugassignment.Profile_Module.sub_module


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.NewMember2
import com.example.drugassignment.Profile_Module.Adapter
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentMemberFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_member_fragment.*
import java.lang.reflect.Member

/**
 * A simple [Fragment] subclass.
 */
class member_fragment : Fragment(),MemberAdapter2.OnRestaurantSelectedListener {
    override fun onRestaurantSelected(restaurant: DocumentSnapshot?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var mAdapter: MemberAdapter2
    private lateinit var binding : FragmentMemberFragmentBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_member_fragment, container, false)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setUpButton()




            initFirestore()
        initRecyclerView()
        observeAuthenticationState()

//        val adapter = MemberAdapter()
//        binding.memberRecycleView.adapter = adapter
//
////        val nights = listOf(
////            "Something",
////            "something2",
////            "something3"
////        )
//
//        val list = arrayListOf<OtherUser>()
//        for (i in 0..100)
//            list.add(OtherUser("Kuek", "kuekyb@gmail","Klang","30 days"))
//        adapter.data = list

        return binding.root
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        // Get the 50 highest rated restaurants
        mQuery = mFirestore
            .collection("User")

    }

    private fun initRecyclerView() {
//        mAdapter = object : MemberAdapter2(mQuery, this@member_fragment) {
//            override fun onError(e: FirebaseFirestoreException?) { // Show a snackbar on errors
////                Log.i("testing",mQuery.toString())
//            }
//        }
//        Log.i("testing", mAdapter.toString())
//
//        binding.memberRecycleView.layoutManager = LinearLayoutManager(context)
//        binding.memberRecycleView.adapter = mAdapter

            var user = FirebaseAuth.getInstance().currentUser


            var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
            //var user2 : CurrentUser
            val email : String = user?.email?:""
            val docRef = mFirestore.collection("User")
                .document(email)
                .collection("SubUser")

            Log.i("user", docRef.get().isSuccessful.toString())

            docRef
                .get().addOnSuccessListener { documentSnapshot ->
                    Log.i("user22", user?.email)
                    val adapter = documentSnapshot.toObjects(SubUser::class.java)
//                    Log.i("testing", adapter.get(0).email)
                    val adapter2 = MemberAdapter()
                    binding.memberRecycleView.adapter = adapter2
                    adapter2.data = adapter
                }

    }

    private fun setUpButton() {
        val fab : FloatingActionButton = activity!!.findViewById(R.id.fab2)
        fab.isVisible = false
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)

        Log.i("avaiable", loginViewModel.currentUser.value!!.availability.toString())

    }

//    private var function = {
//        val nextFrag: NewMember2 = NewMember2();
//        getActivity()!!.getSupportFragmentManager().beginTransaction()
//            .replace(R.id.fragment_new_member2, nextFrag, "findThisFragment")
//            .addToBackStack(null)
//            .commit();
//    }

    private fun observeAuthenticationState() {
        val btn : Button = activity!!.findViewById(R.id.buttonOtherUser)
        loginViewModel.authenticationState?.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    loginViewModel.setCurrentUser()
                }
            }
        })
        loginViewModel.currentUser.observe(this, Observer {
            loginViewModel.currentUser.observe(this, Observer {
                if (it.role == "Mentee") {
                    if(it.availability) {
                        btn.isVisible = true
                        btn.text = "Find Mentor"
//                        btn.setOnClickListener {
//                            function()
//                        }
                    }else {
                        btn.isVisible = false
                    }
                } else {

                        btn.isVisible = true
                        btn.text = "Find Mentee"

                }
            })


        })
    }

}
