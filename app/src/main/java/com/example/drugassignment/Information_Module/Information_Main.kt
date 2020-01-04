package com.example.drugassignment.Information_Module


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentInformationMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query





/**
 * A simple [Fragment] subclass.
 */
class Information_Main : Fragment(), DrugDetailAdapter.OnRestaurantSelectedListener {
    override fun onRestaurantSelected(restaurant: DocumentSnapshot?) {
        Log.i("123","clicked")
    }

    private lateinit var binding : FragmentInformationMainBinding
    private val viewModel by viewModels<InfoViewModel>()

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var mAdapter: DrugDetailAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_information__main, container, false
        )



        val tabLayout : TabLayout = binding.infoTabLayout

        // set tab name
        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("empathogen"))
        tabLayout.addTab(tabLayout.newTab().setText("psychedelic"))

        val text = tabLayout.getTabAt(0)?.text.toString()

        setRecycleDisplay(text)

        initFirestore()
        initRecyclerView()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {


            override fun onTabSelected(tab: TabLayout.Tab?) {
                //setRecycleDisplay(tab?.text.toString())
                var query: Query = mFirestore.collection("DrugInfo")
                if (tab!!.text == "All") {
                    query = mFirestore
                        .collection("DrugInfo")
                        .orderBy("DrugType", Query.Direction.DESCENDING)
                        .limit(LIMIT.toLong())
                } else {
                    query = query.whereEqualTo("DrugType", tab!!.text)
                }
                mQuery = query

//                Log.i("Error", mQuery.)
                mAdapter.setQuery(mQuery)
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

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        // Get the 50 highest rated restaurants
        mQuery = mFirestore
            .collection("DrugInfo")
            .orderBy("DrugType", Query.Direction.DESCENDING)
            .limit(LIMIT.toLong())

        Log.i("123",mQuery.toString())


//        val docRef = mFirestore.collection("DrugInfo").document()
//        mQuery.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: $document.")
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
    }

    private fun initRecyclerView() {
        mAdapter = object : DrugDetailAdapter(mQuery, this@Information_Main) {
            override fun onDataChanged() { // Show/hide content if the query returns empty.
//                if (itemCount == 0) {
//                    binding.infoMainRecycleView.visibility = View.GONE
////                    view_empty.visibility = View.VISIBLE
//                } else {
//                    binding.infoMainRecycleView.visibility = View.VISIBLE
//                    //view_empty.visibility = View.GONE
//                }
            }

            override fun onError(e: FirebaseFirestoreException?) { // Show a snackbar on errors
                Snackbar.make(view!!.findViewById(android.R.id.content),
                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }
        binding.infoMainRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.infoMainRecycleView.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        // Start listening for Firestore updates
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
        private const val LIMIT = 50
    }


}

