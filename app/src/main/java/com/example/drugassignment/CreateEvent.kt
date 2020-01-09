package com.example.drugassignment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.databinding.ActivityCreateEventBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CreateEvent : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEventBinding
    private lateinit var date: Calendar
    private lateinit var startTime: Calendar
    private lateinit var endTime: Calendar
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_event)
        db = FirebaseFirestore.getInstance()

        binding.confirm.setOnClickListener {
            confirm()
        }

        binding.reset.setOnClickListener {
            //reset
        }

        binding.editEndTimeLayout.setEndIconOnClickListener {
            selectEndTime()
        }

        binding.editStartTimeLayout.setEndIconOnClickListener {
            selectStartTIme()
        }

        binding.editDateLayout.setEndIconOnClickListener {
            selectDate()
        }

        date = Calendar.getInstance()
        startTime = Calendar.getInstance()
        endTime = Calendar.getInstance()

    }

    @SuppressLint("SetTextI18n")
    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                //Display Selected date in textbox
                binding.editDate.setText("" + dayOfMonth + " " + (monthOfYear + 1) + ", " + year)

                date.set(Calendar.YEAR, year)
                date.set(Calendar.DAY_OF_YEAR, dayOfMonth)
                date.set(Calendar.MONTH, monthOfYear)

                startTime.set(Calendar.YEAR, year)
                startTime.set(Calendar.DAY_OF_YEAR, dayOfMonth)
                startTime.set(Calendar.MONTH, monthOfYear)

                endTime.set(Calendar.YEAR, year)
                endTime.set(Calendar.DAY_OF_YEAR, dayOfMonth)
                endTime.set(Calendar.MONTH, monthOfYear)

            },
            year,
            month,
            day
        )

        dpd.show()
    }

    private fun selectStartTIme() {
        // cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            startTime.set(Calendar.HOUR_OF_DAY, hour)
            startTime.set(Calendar.MINUTE, minute)
            binding.editStartTime.setText(SimpleDateFormat("HH:mm").format(startTime.time))
        }

        TimePickerDialog(
            this,
            timeSetListener,
            startTime.get(Calendar.HOUR_OF_DAY),
            startTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun selectEndTime() {
        // cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            endTime.set(Calendar.HOUR_OF_DAY, hour)
            endTime.set(Calendar.MINUTE, minute)
            binding.editEndTime.setText(SimpleDateFormat("HH:mm").format(startTime.time))
        }

        TimePickerDialog(
            this,
            timeSetListener,
            endTime.get(Calendar.HOUR_OF_DAY),
            endTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun confirm() {
        val sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString(getString(R.string.passEmail),"123")
        val eventName = binding.editEventName.text.toString()
        val eventDescription = binding.editDescription.text.toString()
        val eventLocation = binding.editEventVenue.text.toString()
        val eventCity = binding.editCity.text.toString()
        val date = date.time
        val startTime = startTime.time
        val endTime = endTime.time


        val event = CreateDrugEvent(
            eventName, eventDescription, eventLocation, eventCity,email
            , date, startTime, endTime
        )

        Toast.makeText(
            this, "Creating Event... Please Wait",
            Toast.LENGTH_SHORT
        ).show()

        // update event collection
        db.collection("Event")
            .add(event)
            .addOnCompleteListener {
                // update creator notification
                val createTime = Date()
                val content = "You have created event : $eventName"
                val notification = Notification(createTime, content, false)

                db.collection("User")
                    .document(email!!)
                    .collection("Notification")
                    .document(createTime.toString())
                    .set(notification)
                    .addOnCompleteListener {
                        Toast.makeText(
                            this, "Successfully Add Noti",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
    }
}
