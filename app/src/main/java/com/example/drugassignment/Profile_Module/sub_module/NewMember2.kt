package com.example.drugassignment.Profile_Module.sub_module


import android.content.Context
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentNewMember2Binding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

/**
 * A simple [Fragment] subclass.
 */
class NewMember2 : Fragment() {

    private lateinit var binding: FragmentNewMember2Binding
    private lateinit var dataListener: ListenerRegistration
    private lateinit var adapter2: MentorMenteeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_member2, container, false
        )

        //  initRecyclerView()

        binding.editSearchLayout.setEndIconOnClickListener {
            searchItem()
        }

        binding.buttonClear.setOnClickListener {
            binding.editSearch.text!!.clear()
            initRecyclerView()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val email = activity!!.intent.getStringExtra(getString(R.string.passEmail))
//        val role = activity?.intent?.getStringExtra(getString(R.string.passRole))
        var role = activity?.intent?.getStringExtra(getString(R.string.passRole))

        role = if (role == "Mentee") {
            "Mentor"
        } else {
            "Mentee"
        }
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        val docRef = mFirestore.collection("User")
            .whereEqualTo("role", role)
            .whereEqualTo("availability", true)

        dataListener = docRef
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(CurrentUser::class.java)
                    adapter2 = MentorMenteeAdapter(this.activity!!)
                    binding.memberListRecycleView.adapter = adapter2
                    adapter2.data = adapter
                }
            }


    }

    override fun onPause() {
        super.onPause()
        dataListener.remove()
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun startListen() {

    }

    private fun searchItem() {
        hideKeyboard()
        val query: Query
        val searchItem = binding.editSearch.text.toString()
        val mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

        mFirestore
            .collection("User")
            .whereEqualTo("email", searchItem)
            .get()
            .addOnCompleteListener {
                val adapter = it.result!!.toObjects(CurrentUser::class.java)
                adapter2.data = adapter
            }

        dataListener.remove()

    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
}
