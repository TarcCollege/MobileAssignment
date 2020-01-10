package com.example.drugassignment.Map_Module


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Login_Registration.LoginViewModel
import com.example.drugassignment.R
import com.example.drugassignment.databinding.FragmentEventDetailBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EventDetail : Fragment() {

    private lateinit var binding: FragmentEventDetailBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var viewModel2: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_event_detail, container, false
        )

        viewModel = ViewModelProviders.of(this.activity!!).get(EventViewModel::class.java)
        viewModel2 = ViewModelProviders.of(this.activity!!).get(LoginViewModel::class.java)

        setUpUi()

        return binding.root
    }

    private fun setUpUi() {
        binding.textDisplayEventName.text = viewModel.detail.eventName
        binding.textDisplayCityDetail.text = viewModel.detail.eventCity
        binding.textDisplayDateDetail.text =
            SimpleDateFormat("dd/MM/yyyy  E").format(viewModel.detail.eventDate)
        binding.textDisplayStartTimeDetail.text =
            SimpleDateFormat("HH : mm a").format(viewModel.detail.startTime)
        binding.textDisplayEndTimeDetail.text =
            SimpleDateFormat("HH : mm a").format(viewModel.detail.endTime)
        binding.textDisplayLocationDetail.text = viewModel.detail.eventLocation
        binding.txtDrugInfo.text = viewModel.detail.eventDescription
        binding.btnparticipate.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val role = sharedPreferences?.getString(
            context?.getString(com.example.drugassignment.R.string.passRole),
            "123"
        )
        val email = viewModel2.currentUser.value?.email
        val eventName = viewModel.detail.eventName

        // check if the record exist or not,
        // if exist mean he participate before, so cannot participate again
        db.collection("User")
            .document(email!!)
            .collection("Event")
            .document(viewModel.detail.eventDate.toString())
            .get()
            .addOnCompleteListener {
                if (!it.result!!.exists()) {
                   // update Current User Event
                    Toast.makeText(
                        context, "Registering Event ...",
                        Toast.LENGTH_SHORT
                    ).show()
                            db.collection("User")
                                .document(email!!)
                                .collection("Event")
                                .document(viewModel.detail.eventDate.toString())
                                .set(viewModel.detail)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        context, "Successfully Register Event",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // update User Notification
                                    // add notification
                                    val createTime = Date()
                                    val content = "You have Register For $eventName"
                                    val notification = Notification(createTime, content, false)

                                    db.collection("User")
                                        .document(email!!)
                                        .collection("Notification")
                                        .document(createTime.toString())
                                        .set(notification)
                                }
                } else {
                    Toast.makeText(
                        context, "You Had Registered This Event",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }


}
