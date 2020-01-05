package com.example.drugassignment.Information_Module


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugassignment.Class.DrugDetail
import com.example.drugassignment.Class.DrugTitle
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentInformationMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_profile.*


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
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_information__main, container, false
        )

        tabLayout = binding.infoTabLayout

        // set tab name

        initFirestore()
        initRecyclerView()
        iniTab()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //setRecycleDisplay(tab?.text.toString())
                var query: Query = mFirestore.collection("DrugInfo")
                query = if (tab!!.text == "all") {
                    mFirestore
                        .collection("DrugInfo")
                        .orderBy("drugName", Query.Direction.ASCENDING)


                } else {
                    val tabb = tab.text.toString().toLowerCase()
                    Log.i("tabb", tabb)
                    query.whereEqualTo("drugType", tabb)
                        .orderBy("drugName", Query.Direction.ASCENDING)
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

//        binding.button.setOnClickListener {
//        }

        binding.editSearchLayout.setEndIconOnClickListener{
            searchItem()
        }

//        binding.editAddress.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                searchItem()
//            }
//
//        })

        return binding.root
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        // Get the 50 highest rated restaurants
        mQuery = mFirestore
            .collection("DrugInfo")
            .orderBy("drugType", Query.Direction.DESCENDING)
            .limit(LIMIT.toLong())

        Log.i("123",mQuery.toString())
    }

    private fun initRecyclerView() {
        mAdapter = object : DrugDetailAdapter(mQuery, this@Information_Main) {
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

    fun iniTab() {
        val docRef = mFirestore.collection("DrugType")
        docRef.get().addOnSuccessListener { texts ->
            val x = texts.toObjects(DrugTitle::class.java)

            for (a in x[0].Type) {
                tabLayout.addTab(tabLayout.newTab().setText(a))
            }

        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
        private const val LIMIT = 50
    }

    private fun searchItem() {
        hideKeyboard()
        val query: Query
        val searchItem = binding.editAddress.text.toString().capitalize()
        query = mFirestore
                .collection("DrugInfo")
                .whereEqualTo("drugName", searchItem)
            mQuery = query
            mAdapter.setQuery(mQuery)

    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken,0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }


}


